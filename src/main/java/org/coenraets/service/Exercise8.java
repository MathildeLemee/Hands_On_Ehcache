package org.coenraets.service;

import net.sf.ehcache.pool.sizeof.AgentSizeOf;
import org.coenraets.model.Wine;
import org.coenraets.util.WineBuilder;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This should create long GC pauses then Full GCs
 * We generate entities that we reference in a collection
 * the collections are stored in a queue and removed after some time
 * So we fill memory faster than the reference is removed, thus garbage collected
 * this is to try to mimic the behaviour of a classic application : Memory is used a lot (i.e. for a session)
 * and is getting filled faster than the GC can cope with
 *
 * @author Aurelien Broszniowski
 *         use with vm options:
 *         -verbose:gc -Xms500m -Xmx4G  -XX:NewRatio=3 -server
 */
public class Exercise8 {

  static int Mb = 1024 * 1024;

  public static void main(String[] args) {
    Runtime runtime = Runtime.getRuntime();

    Map<Integer, Wine> container = new HashMap<Integer, Wine>();

    AgentSizeOf sizeOfEngine = new AgentSizeOf();
    for (int i = 0; i < 10; i++) {
      System.out
          .println("Size of an object: " + sizeOfEngine.deepSizeOf(1000, true, WineBuilder.next()).getCalculated());
    }

    Producer producer = new Producer(container);
    Consumer consumer = new Consumer(container);

    producer.start();
    consumer.start();

    try {
      producer.join();
      consumer.join();
    } catch (InterruptedException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }

  }

  public static class Producer extends Thread {

    Runtime runtime = Runtime.getRuntime();
    SecureRandom rnd = new SecureRandom();
    int cnt = 0;
    Map<Integer, Wine> container;

    public Producer(Map<Integer, Wine> container) {
      this.container = container;
    }

    @Override
    public void run() {
      System.out.println("Producer started....");

      while (true) {
        if (cnt > 14000000) {
          container.remove(rnd.nextInt(container.size()));
        }
        container.put(cnt++, WineBuilder.next());
      }
    }

  }

  public static class Consumer extends Thread {

    Random rnd = new Random();
    Map<Integer, Wine> container;

    public Consumer(Map<Integer, Wine> container) {
      this.container = container;
    }

    @Override
    public void run() {
      System.out.println("Consumer started....");
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }

      while (true) {
        int size = container.size();
        int key = rnd.nextInt(size);
        long start = System.currentTimeMillis();
        container.get(key);
        long end = System.currentTimeMillis();
        if (size % 100000 == 0) {
          System.out.println("Time taken to read from map: " + (end - start) + "ms for a size of " + size);
        }
      }
    }
  }

}