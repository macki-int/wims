package com.mj.wims.config;

import com.mj.wims.model.User;
import com.mj.wims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/**").hasRole("ADMIN")
//                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST,"/product-types").permitAll()
                .antMatchers("/users").permitAll()
                .antMatchers("/users/**").permitAll()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/employees").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/employees/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PATCH, "/employees/**").hasRole("ADMIN");
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByUserName(username).get();
                return (UserDetails) user;
            }
        }).passwordEncoder(new BCryptPasswordEncoder());
    }
}
