package org.coenraets.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.MemoryUnit;
import net.sf.ehcache.pool.sizeof.AgentSizeOf;
import org.coenraets.util.WineBuilder;

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
 *         -verbose:gc -Xms500m -Xmx500m  -XX:NewRatio=3 -server -XX:MaxDirectMemorySize=10G
 */
public class Exercise10 {

  static int Mb = 1024 * 1024;

  public static void main(String[] args) {
    Runtime runtime = Runtime.getRuntime();

    Configuration config = new Configuration().cache(
        new CacheConfiguration().name("offheap")
            .maxBytesLocalHeap(300, MemoryUnit.MEGABYTES)
            .maxBytesLocalOffHeap(1, MemoryUnit.GIGABYTES)
    );
    CacheManager cacheManager = CacheManager.newInstance(config);
    Cache container = cacheManager.getCache("offheap");


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
    Random rnd = new Random();
    int cnt = 0;
    Ehcache container;

    public Producer(Ehcache container) {
      this.container = container;
    }

    @Override
    public void run() {
      System.out.println("Producer started....");
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }

      while (true) {
//        if (container.calculateOffHeapSize() > 0 && container.calculateOffHeapSize() < 200 * Mb) {
        if (cnt > 14000000) {
          container.remove(rnd.nextInt(container.getSize()));
        }
        container.put(new Element(cnt++, WineBuilder.next()));
      }
    }
  }

  public static class Consumer extends Thread {

    Random rnd = new Random();
    Ehcache container;

    public Consumer(Ehcache container) {
      this.container = container;
    }

    @Override
    public void run() {
      System.out.println("Consumer started....");
      try {
        sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }

      while (true) {
        int size = container.getSize();
        int key = rnd.nextInt(size);
        long start = System.currentTimeMillis();
        container.get(key);
        long end = System.currentTimeMillis();
        if (size % 1000 == 0) {
          System.out.println("Time taken to read from map: " + (end - start) + "ms for a size of " + size);
        }
      }
    }
  }
}