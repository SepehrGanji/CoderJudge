package com.coder.judge.Queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Queue {
    public static Channel getChannel() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(QueueConfig.getQueueHost());
        factory.setUsername(QueueConfig.getQueueUsername());
        factory.setPassword(QueueConfig.getQueuePassword());
        try {
            Connection connection = factory.newConnection();
            return connection.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
