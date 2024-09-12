package com.coder.judge.Serializer;

import com.coder.judge.Model.Submission;
import com.google.gson.Gson;

public class SubmissionSerializer {

    private SubmissionSerializer() {
    }

    private static class SingletonHolder {

        private static final SubmissionSerializer INSTANCE = new SubmissionSerializer();
    }

    public static SubmissionSerializer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Submission deserialize(String message) {
        Gson gson = new Gson();
        Submission sub = gson.fromJson(message, Submission.class);
        return sub;
    }
}
