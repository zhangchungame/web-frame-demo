package com.dandinglong.webframedemo.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

@Component
public class RabbitTemplateReturnCallbackConfig implements RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setReturnCallback(this);            //指定 ConfirmCallback
    }
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        try {
            System.out.println(this.getClass().getName()+"message = "+(new String(message.getBody(),"utf-8"))+" exchange = "+exchange+" routingKey = "+routingKey);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
