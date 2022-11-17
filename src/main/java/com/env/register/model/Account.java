/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.env.register.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Builder
@Data
@AllArgsConstructor
public class Account {
    private String userName;
    private String password;
    private String phoneNumber;

    public String toOutput() {
        return String.format("%s %s", this.userName, this.password);
    }

    public String generateEmail() {
        return String.format("%s@gmail.com", this.userName.toLowerCase());
    }
}
