//package org.coenraets;
//
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Element;
//import net.sf.ehcache.config.CacheConfiguration;
//import net.sf.ehcache.config.CacheWriterConfiguration;
//import net.sf.ehcache.config.Configuration;
//import net.sf.ehcache.config.TerracottaClientConfiguration;
//import net.sf.ehcache.config.TerracottaConfiguration;
//import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * A JPA-based implementation of the Booking Service. Delegates to a JPA entity manager to issue data access calls
// * against the backing repository. The EntityManager reference is provided by the managing container (Spring)
// * automatically.
// */
//@Service("bookingServiceEhCache")
//@Repository
//public class EhcacheBookingService implements BookingService {
//
//  @Autowired
//  BookingService bookingService;
//
//  @Autowired
//  MyCacheWriter myCacheWriter;
//
//  @Autowired
//  MyCacheEntryFactory myCacheEntryFactory;
//
//
//  private CacheManager manager;
//  private Cache cacheBookings;
//  private Cache cacheHotels;
//  private Cache cacheHotelsSoR;
//  private SelfPopulatingCache selfPopulatingCache;
//
//  public EhcacheBookingService() {
//    Configuration configuration = new Configuration()
//        .cache(new CacheConfiguration("cacheHotels", 3)
//        );
//    this.manager = new CacheManager(configuration);
//    this.cacheBookings = manager.getCache("cacheBooking");
//    this.cacheHotels = manager.getCache("cacheHotels");
//
//  }
//
//  public void writeThrough() {
//    cacheHotelsSoR.registerCacheWriter(myCacheWriter);
//  }
//
//  public void writeBehind() {
//    Configuration configuration = new Configuration()
//        .terracotta(new TerracottaClientConfiguration().url("localhost:9510"))
//        .cache(new CacheConfiguration("WriteLongBehindCache", 1000).terracotta(new TerracottaConfiguration()
//        )
//            .cacheWriter(new CacheWriterConfiguration()
//                .writeMode(CacheWriterConfiguration.WriteMode.WRITE_BEHIND)
//                .maxWriteDelay(30)
//                .writeBatching(true)
//                .writeBatchSize(7000)
//                .writeBehindConcurrency(2)
//            )
//        );
//
//  }
//
//  public void readThrough() {
//    selfPopulatingCache = new SelfPopulatingCache(cacheHotelsSoR, myCacheEntryFactory);
//
//  }
//
//  public List<Booking> findBookings(String username) {
//    Element username1 = cacheBookings.get("username");
//    if (username1 != null && username1.getObjectValue() != null) {
//      return (List<Booking>)username1.getObjectValue();
//    } else {
//      return bookingService.findBookings(username);
//    }
//  }
//
//  public List<Hotel> findHotels(SearchCriteria criteria) {
//    return bookingService.findHotels(criteria);
//  }
//
//  public Hotel findHotelById(Long id) {
//    Element username1 = cacheHotels.get(id);
//    if (username1 != null && username1.getObjectValue() != null) {
//      return (Hotel)username1.getObjectValue();
//    } else {
//      Hotel hotelById = bookingService.findHotelById(id);
//      cacheHotels.put(new Element(id, hotelById));
//      return hotelById;
//    }
//  }
//
//
//  public Hotel cacheAsideRead(Long id) {
//    Element username1 = cacheHotels.get(id);
//    if (username1 != null && username1.getObjectValue() != null) {
//      return (Hotel)username1.getObjectValue();
//    } else {
//      Hotel hotelById = bookingService.findHotelById(id);
//      cacheHotels.put(new Element(id, hotelById));
//      return hotelById;
//    }
//  }
//
//  public Booking createBooking(Long hotelId, String username) {
//    return bookingService.createBooking(hotelId, username);
//  }
//
//  public void persistBooking(Booking booking) {
//    bookingService.persistBooking(booking);
//  }
//
//  public void cancelBooking(Long id) {
//    bookingService.cancelBooking(id);
//
//  }
//
//  @Override
//  public void createHotel(final Hotel objectValue) {
//    //To change body of implemented methods use File | Settings | File Templates.
//  }
//
//}