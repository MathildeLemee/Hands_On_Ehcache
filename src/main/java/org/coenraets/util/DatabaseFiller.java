package org.coenraets.util;

import org.coenraets.service.WineMysql;
import org.coenraets.service.WineService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Aurelien Broszniowski
 */
public class DatabaseFiller {

  public static void main(String[] args) {
    final WineService mysql = new WineMysql();
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    List<Callable<Object>> tasksList = new ArrayList<Callable<Object>>(4);
    final WineBuilder wineBuilder = new WineBuilder();

    for (int i = 0; i < 4; i++) {
      tasksList.add(new Callable<Object>() {
        @Override
        public Object call() throws Exception {
          for (int i = 0; i < 1000; i++) {
            mysql.create(wineBuilder.next());
          }
          return null;
        }
      });
    }
    try {
      List<Future<Object>> futureList = executorService.invokeAll(tasksList);
    } catch (InterruptedException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }

    executorService.shutdown();

  }
}
