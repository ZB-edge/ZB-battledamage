package cn.edu.bjtu.battledamage.service.Impl;

import cn.edu.bjtu.battledamage.service.MqConsumer;
import cn.edu.bjtu.battledamage.service.MqFactory;
import cn.edu.bjtu.battledamage.service.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MqFactoryImpl implements MqFactory {
    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;
    @Value("${mq}")
    private String name;

    @Override
    public MqProducer createProducer(){
        return new ActiveMqProducerImpl(jmsMessagingTemplate);
    }

    @Override
    public MqConsumer createConsumer(String topic){
        return new ActiveMqConsumerImpl(topic);
    }
}
