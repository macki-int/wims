package com.mj.wims.service;

public interface PasswordCompareService {
    public boolean comparePassword(String actualPassword, String incomingPassword);
}
