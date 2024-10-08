package com.coder.judge.Queue;

import java.nio.charset.StandardCharsets;

import org.apache.log4j.Logger;

import com.coder.judge.DB.SubmissionEntity;
import com.coder.judge.FileSystem.SubmissionFS;
import com.coder.judge.Model.Submission;
import com.coder.judge.Serializer.SubmissionSerializer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class SubmissionCallback implements DeliverCallback {

    private static final Logger log = Logger.getLogger(SubmissionCallback.class.getName());

    @Override
    public void handle(String consumerTag, Delivery delivery) {
        try {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            Submission submission = SubmissionSerializer.getInstance().deserialize(message);
            
            SubmissionFS.getInstance().save(submission);
            SubmissionEntity.getInstance().insert(submission);
            log.info("Saved submission: " + submission.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
