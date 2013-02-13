//package org.coenraets.replication; /**
// *  Copyright 2003-2008 Luck Consulting Pty Ltd
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//
//
//import net.sf.ehcache.distribution.wan.jms.JMSUtil;
//import org.apache.activemq.jndi.ActiveMQInitialContextFactory;
//
//import java.net.URISyntaxException;
//import java.util.Hashtable;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import javax.naming.Context;
//import javax.naming.NamingException;
//
//public class TestActiveMQInitialContextFactory extends ActiveMQInitialContextFactory {
//
//  /**
//   * Creates an initial context with
//   * {@inheritDoc}
//   */
//  @Override
//  @SuppressWarnings("unchecked")
//  public Context getInitialContext(Hashtable environment) throws NamingException {
//
//    Map<String, Object> data = new ConcurrentHashMap<String, Object>();
//
//    String replicationTopicConnectionFactoryBindingName = (String) environment.get(JMSUtil.TOPIC_CONNECTION_FACTORY_BINDING_NAME);
//
//    if (replicationTopicConnectionFactoryBindingName != null) {
//      try {
//        data.put(replicationTopicConnectionFactoryBindingName, createConnectionFactory(environment));
//      } catch (URISyntaxException e) {
//        throw new NamingException("Error initialisating TopicConnectionFactory with message " + e.getMessage());
//      }
//    }
//
//    String getQueueConnectionfactoryBindingName = (String) environment.get(JMSUtil.GET_QUEUE_CONNECTION_FACTORY_BINDING_NAME);
//
//    if (getQueueConnectionfactoryBindingName != null) {
//      try {
//        data.put(getQueueConnectionfactoryBindingName, createConnectionFactory(environment));
//      } catch (URISyntaxException e) {
//        throw new NamingException("Error initialisating TopicConnectionFactory with message " + e.getMessage());
//      }
//    }
//
//    String replicationTopicBindingName = (String) environment.get(JMSUtil.REPLICATION_TOPIC_BINDING_NAME);
//    if (replicationTopicBindingName != null) {
//      data.put(replicationTopicBindingName, createTopic(replicationTopicBindingName));
//    }
//
//
//    String getQueueBindingName = (String) environment.get(JMSUtil.GET_QUEUE_BINDING_NAME);
//    if (getQueueBindingName != null) {
//      data.put(getQueueBindingName, createQueue(getQueueBindingName));
//    }
//
//    return createContext(environment, data);
//  }
//}
