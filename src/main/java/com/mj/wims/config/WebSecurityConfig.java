package com.mj.wims.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig {
    private DataSource dataSource;
    private ObjectMapper objectMapper;

    private RestAuthenticationSuccessHandler successHandler;
    private RestAuthenticationFailureHandler failureHandler;

    private String secret;


}
