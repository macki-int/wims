package com.mj.wims.model;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleEnumTest {

    void whenRoleEnumToInteger() {
        //BEFORE
        RoleEnum roleEnum;
        //WHEN
        roleEnum = RoleEnum.ROLE_ADMIN;

        //THEN
        assertEquals(roleEnum.ordinal(), 0);
    }
}