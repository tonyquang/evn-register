package com.env.register.controller;

import com.env.register.gateway.CaptchaGateway;
import com.env.register.gateway.CaptchaGatewayImpl;
import com.env.register.log.MyLogger;
import com.env.register.model.Account;
import com.env.register.model.LogLevel;
import com.env.register.service.EVNRegisterService;
import com.env.register.service.EVNRegisterServiceImpl;
import com.env.register.ui.MainFrame;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@RequiredArgsConstructor
public class EVNRegisterController {
    private final MainFrame mainFrame;
    private EVNRegisterService registerService;

    private boolean running;
    private boolean stopping;

    public void init() {
        mainFrame.getTxta_logs().append("\n");
        mainFrame.getBtn_start().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread(() -> start()).start();
            }
        });
        mainFrame.getBtn_stop().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread(() -> stop()).start();
            }
        });
    }

    public void start() {
        if (running) return;
        List<Account> accounts  = mainFrame.getAccounts();
        if (accounts.size() == 0) return;

        int threads = mainFrame.getThreads();
        if (threads == 0) return;

        String captChaKey = mainFrame.getTxt_2captcha().getText();
        if (captChaKey.trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "API Captcha is empty");
            return;
        }

        CaptchaGateway captchaGateway = new CaptchaGatewayImpl(captChaKey);
        MyLogger logger = new MyLogger(mainFrame);
        registerService = new EVNRegisterServiceImpl(mainFrame, logger, captchaGateway);
        try {
            running = true;
            logger.log(LogLevel.INFO, "", "START", "Started!");
            registerService.doRegister(accounts, threads);
        }catch (Exception e) {}
        finally {
            running = false;
        }
    }

    public void stop() {
        if (stopping) return;
        stopping = true;
        try {
            MyLogger logger = new MyLogger(mainFrame);
            logger.log(LogLevel.INFO, "", "STOP", "Stopping...!");
            registerService.stop();
        }catch (Exception e){}
        finally {
            stopping = false;
        }
    }
}
