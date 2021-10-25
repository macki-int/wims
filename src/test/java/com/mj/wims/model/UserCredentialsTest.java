package com.mj.wims.model;


import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class UserCredentialsTest {

    @Test
    public void whenUserCredentialsCreate(){
        //BEFOR
        UserCredentials userCredentials = new UserCredentials();

        //WHEN
        userCredentials.setUserName("Adam");
        userCredentials.setPassword("AbCd");

        //THEN
        assertEquals(userCredentials.getUserName(),"Adam");
        assertEquals(userCredentials.getPassword(),"AbCd");
    }
}