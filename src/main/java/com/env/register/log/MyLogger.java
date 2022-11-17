package com.env.register.log;

import com.env.register.model.LogLevel;
import com.env.register.ui.MainFrame;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MyLogger {
    private final MainFrame mainFrame;

    public synchronized void log(LogLevel logLevel, String account, String step, String message) {
        this.mainFrame.getTxta_logs().append(
                String.format(
                        "[%s] [%s] %s - %s\n", logLevel, account, step, message
                )
        );
    }
}
