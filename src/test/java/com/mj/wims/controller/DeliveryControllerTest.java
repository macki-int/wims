package com.mj.wims.controller;

import com.mj.wims.repository.DeliveryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DeliveryControllerTest {

    @Autowired
    private DeliveryController deliveryController;

    @Test
    public void userControllerInitializedCorrectly() {
        assertThat(deliveryController).isNotNull();
    }
}