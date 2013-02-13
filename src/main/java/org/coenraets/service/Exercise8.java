package org.coenraets.service;

import net.sf.ehcache.pool.sizeof.AgentSizeOf;
import org.coenraets.model.Wine;
import org.coenraets.util.WineBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This should create long GC pauses then Full GCs
 * We generate entities that we reference in a collection
 * the collections are stored in a queue and removed after some time
 * So we fill memory faster than the reference is removed, thus garbage collected
 * this is to try to mimic the behaviour of a classic application : Memory is used a lot (i.e. for a session)
 * and is getting filled faster than the GC can cope with
 *
 * @author Aurelien Broszniowski
 *      use with vm options:
 *         -verbose:gc -Xms500m -Xmx2G  -XX:NewRatio=3 -server
 */
public class Exercise8 {

  static int Mb = 1024 * 1024;

  public static void main(String[] args) {
    Runtime runtime = Runtime.getRuntime();

    RefKeeper refKeeper = new RefKeeper();

    WineBuilder wineBuilder = new WineBuilder();

    AgentSizeOf sizeOfEngine = new AgentSizeOf();
    for (int i = 0; i < 10; i++) {
      System.out.println("Size of an object: " + sizeOfEngine.deepSizeOf(1000, true, wineBuilder.next()).getCalculated());
    }

    while (true) {
      while (runtime.freeMemory() > 50 * Mb) {

        System.out.println("Free Memory:" + runtime.freeMemory() / Mb);
        long start = System.currentTimeMillis();
        List<Wine> wines = new ArrayList<Wine>();
        for (int i = 0; i < 100000; i++) {
          wines.add(wineBuilder.next());
        }
        refKeeper.addReference(wines);
        long end = System.currentTimeMillis();
        final long length = end - start;
        System.out.println("Time taken to fill List : " + length);
      }
    }
  }

  public static class RefKeeper extends Thread {

    Queue<List<Wine>> queue = new LinkedList<List<Wine>>();


    @Override
    public void run() {
      while (true) {
        try {
          sleep(1000);
        } catch (InterruptedException e) {
          this.interrupt();
        }
        queue.remove();
      }
    }

    public void addReference(List<Wine> wines) {
      queue.add(wines);
    }
  }
}