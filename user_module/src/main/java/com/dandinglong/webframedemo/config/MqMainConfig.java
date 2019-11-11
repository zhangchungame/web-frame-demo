package com.dandinglong.webframedemo.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class MqMainConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;


    @Bean
    public AmqpTemplate getAmqpTemplate(){
        RabbitTemplate template=new RabbitTemplate(connectionFactory);
        RetryTemplate retryTemplate=new RetryTemplate();
        template.setRetryTemplate(retryTemplate);
        template.setMandatory(true);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

}
