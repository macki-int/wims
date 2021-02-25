package com.mj.wims.service;

public class PasswordCompareService {
    public boolean comparePassword(String actualPassword, String incomingPassword){
        return actualPassword.equals(incomingPassword);
    }
}
