package cn.edu.bjtu.battledamage.service;

public interface MqProducer {
    void publish(String topic, String message);
}
