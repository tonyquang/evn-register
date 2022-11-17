package com.env.register.service;

import com.env.register.gateway.CaptchaGateway;
import com.env.register.log.MyLogger;
import com.env.register.model.Account;
import com.env.register.model.LogLevel;
import com.env.register.ui.MainFrame;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class EVNRegisterServiceImpl implements EVNRegisterService{
    private final MainFrame mainFrame;
    private final MyLogger myLogger;
    private final CaptchaGateway captchaGateway;

    private List<SeleniumService> listSeleniumSvc;

    @Override
    public void doRegister(List<Account> accounts, int threads) {
       try {
           listSeleniumSvc = new ArrayList<>();
           WebDriverManager.chromedriver().setup();
           ExecutorService executor = Executors.newFixedThreadPool(threads);
           for(Account account : accounts) {
               SeleniumService seleniumService = new SeleniumService(mainFrame, myLogger, captchaGateway, account);
               listSeleniumSvc.add(seleniumService);
               executor.execute(seleniumService);
           }
           executor.shutdown(); // Không cho threadpool nhận thêm nhiệm vụ nào nữa

           while (!executor.isTerminated()) {
               // Chờ xử lý hết các request còn chờ trong Queue ...
           }
           JOptionPane.showMessageDialog(mainFrame, "Hoan thanh!");
           myLogger.log(LogLevel.INFO, "", "====>", " D O N E <====");
       }catch (Exception e) {
           myLogger.log(LogLevel.ERROR, "doRegister", "", e.getMessage());
       }
    }

    @Override
    public void stop() {
        for (SeleniumService seleniumService : listSeleniumSvc) {
            seleniumService.stop();
        }
    }
}
