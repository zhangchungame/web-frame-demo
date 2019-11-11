package com.dandinglong.webframedemo.config;


import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

@Component
public class RabbitTemplateConfirmCallbackConfig implements RabbitTemplate.ConfirmCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);            //指定 ConfirmCallback
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        try {
            System.out.println(this.getClass().getName()+correlationData.getId()+new String(correlationData.getReturnedMessage().getBody(),"utf-8")+"  ack = "+ack +" cause = "+cause);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
