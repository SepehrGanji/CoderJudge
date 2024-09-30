package com.coder.judge.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QuestionEntity {

    private static QuestionEntity _instance = null;

    private QuestionEntity() {
    }

    public static QuestionEntity getInstance() {
        if (_instance == null) {
            _instance = new QuestionEntity();
        }
        return _instance;
    }

    public int getLimit(int question, String limit) {
        try {
            Connection conn = DB.connection();
            String query = "SELECT " + limit + " FROM question WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, question);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
