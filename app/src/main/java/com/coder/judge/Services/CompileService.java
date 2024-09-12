package com.coder.judge.Services;

public class CompileService implements Runnable {

    private static CompileService _instance = null;

    private CompileService() {
    }

    public static CompileService getInstance() {
        if (_instance == null) {
            _instance = new CompileService();
        }
        return _instance;
    }

    @Override
    public void run() {
        System.out.println("RunService...");
    }
}
