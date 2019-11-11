package com.dandinglong.notice.config;

import com.dandinglong.notice.entity.UserEntity;
import com.rabbitmq.client.ShutdownSignalException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MqMainConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public AmqpTemplate getAmqpTemplate(){
        RabbitTemplate template=new RabbitTemplate(connectionFactory);
        RetryTemplate retryTemplate=new RetryTemplate();
        template.setRetryTemplate(retryTemplate);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setDeBatchingEnabled(true);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(4);
        factory.setPrefetchCount(3);

//        factory.setba
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper());
        return jsonConverter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.dandinglong.webframedemo.entity.UserEntity", UserEntity.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }
}
