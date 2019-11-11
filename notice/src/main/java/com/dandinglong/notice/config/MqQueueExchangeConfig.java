package com.dandinglong.notice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqQueueExchangeConfig {
    @Bean
    DirectExchange getTopic(){
        return new DirectExchange("user_register_exchange",true,false);
    }
    @Bean
    Queue getQueue(){
        return new Queue("user_register_sms",true,false,false);
    }
    @Bean
    Queue getQueue2(){
        return new Queue("user_register_mail",true,false,false);
    }
    @Bean
    Binding getBinding(){
        return BindingBuilder.bind(getQueue()).to(getTopic()).with("user.regist");
    }
    @Bean
    Binding getBinding2(){
        return BindingBuilder.bind(getQueue2()).to(getTopic()).with("user.regist");
    }
}
