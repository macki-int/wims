package com.mj.wims;

import com.mj.wims.controller.ProductController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @Test
    public void userControllerInitializedCorrectly(){
        assertThat(productController).isNotNull();
    }
}
