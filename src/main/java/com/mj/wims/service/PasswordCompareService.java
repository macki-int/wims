package com.mj.wims.service;

public class PasswordCompareService {
    public boolean comparePassword(String newPassword, String oldPassword){
        return newPassword.equals(oldPassword);
    }
}
