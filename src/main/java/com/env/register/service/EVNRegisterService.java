package com.env.register.service;

import com.env.register.model.Account;

import java.util.List;

public interface EVNRegisterService {
    void doRegister(List<Account> accounts, int threads);
    void stop();
}
