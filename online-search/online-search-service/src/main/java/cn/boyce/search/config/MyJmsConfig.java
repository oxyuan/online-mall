package cn.boyce.search.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

/**
 * @Author: Yuan Baiyu
 * @Date: Created in 16:17 2019/5/7
 **/
@Configuration
@EnableJms
public class MyJmsConfig {

    @Bean("jmsQueueListenerContainerFactory")
    public JmsListenerContainerFactory jmsQueueListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 设置连接数
        factory.setConcurrency("3-10");
        // 重连间隔时间
        factory.setRecoveryInterval(1000L);
        factory.setPubSubDomain(false);
        return factory;

    }

    @Bean("jmsTopicListenerContainerFactory")
    public JmsListenerContainerFactory jmsTopicListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 重连间隔时间
        factory.setPubSubDomain(true);
        return factory;

    }
}
