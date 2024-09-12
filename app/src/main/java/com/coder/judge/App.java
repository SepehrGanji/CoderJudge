package com.coder.judge;

import org.apache.log4j.Logger;

import com.coder.judge.Queue.Queue;
import com.coder.judge.Queue.SubmissionCallback;
import com.rabbitmq.client.Channel;

public class App {

    private static final Logger log = Logger.getLogger(App.class.getName());

    public String getGreeting() {
        return "App started!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        try {
            Channel channel = Queue.getChannel();
            String queueName = System.getenv("SUBMISSION_QUEUE_NAME");
            SubmissionCallback cb = new SubmissionCallback();

            channel.basicConsume(queueName, true, cb, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
