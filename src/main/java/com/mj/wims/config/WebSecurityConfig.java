package com.mj.wims.config;

import com.mj.wims.service.AuthenticationFilter;
import com.mj.wims.service.LoginFilter;
import com.mj.wims.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,  jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl customUserDetailsService;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
////                .antMatchers("/*").permitAll()
////                .antMatchers("/login").permitAll()
//                .antMatchers("/*").permitAll()
////                .antMatchers("/*").hasRole("ADMIN")
//                .antMatchers("/swagger-ui/").permitAll()
//                .antMatchers("/v2/api-docs").permitAll()
//                .antMatchers("/webjars/**").permitAll()
//                .antMatchers("/swagger-resources/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
////                .formLogin()
////                .loginPage("/users/login").permitAll()
////                .and()
////                .logout().permitAll()
////                .and()
//                .csrf().disable();
//
////        http.authorizeRequests().antMatchers("/**").permitAll();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()
//                .antMatchers(HttpMethod.PATCH, "/products/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/users").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/users/*").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/users/password").hasAnyRole("USER", "ADMIN")
//                .antMatchers("/products/*").permitAll()
                .antMatchers("/swagger-ui/").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new LoginFilter("/users/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

