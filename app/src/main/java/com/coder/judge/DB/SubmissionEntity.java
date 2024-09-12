package com.coder.judge.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.coder.judge.Model.Submission;

public class SubmissionEntity {

    private static SubmissionEntity _instance = null;

    private SubmissionEntity() {
    }

    public static SubmissionEntity getInstance() {
        if (_instance == null) {
            _instance = new SubmissionEntity();
        }
        return _instance;
    }

    public void insert(Submission submission) {
        try {
            Connection conn = DB.connection();
            String query = "INSERT INTO submission (id, question, status, lang) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, submission.getId());
            stmt.setInt(2, submission.getQuestion());
            stmt.setString(3, "PENDING"); //TODO: Enum for status
            stmt.setString(4, submission.getLang());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
