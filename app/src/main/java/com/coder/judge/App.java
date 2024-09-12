package com.coder.judge;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.coder.judge.Queue.Queue;
import com.coder.judge.Queue.SubmissionCallback;
import com.coder.judge.Services.CompileService;
import com.coder.judge.Services.RunService;
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
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

            channel.basicConsume(queueName, true, cb, consumerTag -> {
            });
            executor.scheduleAtFixedRate(CompileService.getInstance(), 0, 6, TimeUnit.SECONDS);
            executor.scheduleAtFixedRate(RunService.getInstance(), 3, 6, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
