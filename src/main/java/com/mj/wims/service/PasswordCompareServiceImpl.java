package com.mj.wims.service;

public class PasswordCompareServiceImpl implements PasswordCompareService {
    public boolean comparePassword(String actualPassword, String oldPassword){
        return actualPassword.equals(oldPassword);
    }
}
