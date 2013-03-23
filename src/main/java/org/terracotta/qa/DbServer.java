package org.terracotta.qa;

import org.coenraets.model.Wine;
import org.coenraets.util.WineBuilder;
import org.h2.tools.Server;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aurelien Broszniowski
 */
public class DbServer {

  private SessionFactory sessionFactory;
  private Server server;

  public static void main(String[] args) {
    new DbServer().start();
  }

  private void start() {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        if (sessionFactory != null)
          sessionFactory.close();
        if (server != null) {
          server.stop();
        }
      }
    });

    Configuration configuration = new Configuration().configure("/hibernate.cfg.xml");
    configuration.setProperty("hibernate.connection.url", "jdbc:h2:tcp://localhost:8092/data;DB_CLOSE_DELAY=-1;MVCC=TRUE");
    configuration.addResource("Wine.hbm.xml");

    final String[] args = new String[] {
        "-tcpPort", "8092",
        "-tcpAllowOthers", "true" };
    try {
      server = Server.createTcpServer(args).start();
    } catch (SQLException e) {
      if (sessionFactory != null)
        sessionFactory.close();
      if (server != null) {
        server.stop();
      }
      System.exit(-1);
    }

    sessionFactory = configuration.buildSessionFactory();

    System.out.print("Creating DB: ");
    for (int j = 0; j < 10; j++) {
      List<Wine> wines = new ArrayList<Wine>();
      for (int i = 0; i < 10000; i++) {
        wines.add(WineBuilder.next());
      }
      saveEntities(sessionFactory, wines);
      System.out.print(".");
    }
    System.out.println("... Done!");

    try {
      Thread.currentThread().join(24 * 60 * 60 * 1000);
    } catch (InterruptedException e) {
      sessionFactory.close();
      if (sessionFactory != null)
        sessionFactory.close();
      if (server != null) {
        server.stop();
      }
    }
  }

  public void saveEntities(SessionFactory sessionFactory, final List<?> entities) {
    Session session = sessionFactory.getCurrentSession();
    org.hibernate.Transaction tx = null;
    try {
      tx = session.beginTransaction();

      for (Object entity : entities) {
        session.save(entity);
      }
      session.flush();

      tx.commit();
    } catch (HibernateException he) {
      if (tx != null) tx.rollback();
      throw he;
    }
  }


}
