package com.coder.judge.Queue;

public class QueueConfig {
    public static String getQueueHost() {
        return System.getenv("QUEUE_HOST");
    }

    public static String getQueueUsername() {
        return System.getenv("QUEUE_USER");
    }

    public static String getQueuePassword() {
        return System.getenv("QUEUE_PASSWD");
    }

    public static String getSubmissionQueueName() {
        return System.getenv("SUBMISSION_QUEUE_NAME");
    }

    public static String getResultQueueName() {
        return System.getenv("RESULT_QUEUE_NAME");
    }
}
