package com.mj.wims.service;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordCompareServiceImpl implements PasswordCompareService {
    public boolean comparePassword(String actualPassword, String oldPassword){
        return BCrypt.checkpw(oldPassword, actualPassword);
    }
}
