package com.dandinglong.notice.mq;

import com.alibaba.fastjson.JSON;
import com.dandinglong.notice.entity.UserEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class UserConsumer {
//    @Autowired
//    RabbitTemplate rabbitTemplate;
    @Autowired
    private Jackson2JsonMessageConverter jackson2JsonMessageConverter;
    @RabbitListener(queues = "user_register_sms")
    public void dealRegisterSms(Message message, Channel channel){
        UserEntity userEntity=(UserEntity)jackson2JsonMessageConverter.fromMessage(message);
        System.out.println("收到sms消息"+ JSON.toJSONString(userEntity));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RabbitListener(queues = "user_register_mail")
    public void dealRegisterMail(Message message, Channel channel){
        UserEntity userEntity=(UserEntity)jackson2JsonMessageConverter.fromMessage(message);
        System.out.println("收到mail消息"+ JSON.toJSONString(userEntity));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
