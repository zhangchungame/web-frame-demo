package com.dandinglong.webframedemo.service;

import com.dandinglong.webframedemo.dao.master.UserMapper;
import com.dandinglong.webframedemo.entity.UserEntity;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Long getUUid(){
        RedisAtomicLong entityIdCounter = new RedisAtomicLong("AutoUUid", redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        return increment;
    }

    public UserEntity register(UserEntity userEntity){
        userMapper.myinsertOne(userEntity);
        sendRegisterMq(userEntity);
        return userEntity;
    }
    private void sendRegisterMq(UserEntity userEntity){
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance().setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        Message message = new Jackson2JsonMessageConverter().toMessage(userEntity, messageProperties);
        CorrelationData correlationData=new CorrelationData(getUUid().toString());
        correlationData.setReturnedMessage(message);
        rabbitTemplate.convertAndSend("user_register_exchange","user.regist",message,correlationData);
    }
    @Cacheable(value = "allUser",key = "'info'")
    public List<UserEntity> selectAllUser(){
        return userMapper.selectAllUser();
    }

    @Cacheable(value = "user", key = "#root.args[0]", unless = "#result eq null ")
    @Transactional(transactionManager = "masterTransactionManager")
    public UserEntity select(int userId) {
        UserEntity userEntity = userMapper.myselectById(userId);
//        throw new RuntimeException("错误");
        return userEntity;
    }
    @CachePut(value = "user",key = "#result.id")
//    @Transactional
    public UserEntity insert(UserEntity userEntity) {
        userMapper.myinsertOne(userEntity);
        return userEntity;
    }


    @Caching(evict = {
            @CacheEvict(value = "user",key = "#root.args[0]"),
            @CacheEvict(value = "allUser",key = "'info'")
    })
//    @Transactional
    public UserEntity delete(int userId) {
        UserEntity userEntity = new UserEntity();
        userMapper.deleteById(userId);
        return userEntity;
    }
}
