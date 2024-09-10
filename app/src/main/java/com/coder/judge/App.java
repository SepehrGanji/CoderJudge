package com.coder.judge;


import org.apache.log4j.Logger;  

import com.coder.judge.Queue.Queue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class App {
    static Logger log = Logger.getLogger(App.class.getName());  
    public String getGreeting() {
        log.info("testlog");
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        try {
            Channel channel = Queue.getChannel();
            //channel.queueDeclare("test", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume("test", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
