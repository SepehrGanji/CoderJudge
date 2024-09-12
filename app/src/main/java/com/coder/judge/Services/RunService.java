package com.coder.judge.Services;

import org.apache.log4j.Logger;

import com.coder.judge.FileSystem.FileRunner;

public class RunService implements Runnable {

    private static RunService _instance = null;
    private static final Logger log = Logger.getLogger(CompileService.class.getName());

    private RunService() {
    }

    public static RunService getInstance() {
        if (_instance == null) {
            _instance = new RunService();
        }
        return _instance;
    }

    @Override
    public void run() {
        FileRunner.getInstance().runFile("compile.py");
    }
}
