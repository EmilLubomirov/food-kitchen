package com.example.foodkitchen.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("*").permitAll()
                .antMatchers("/css/*", "/img/*", "/js/*").permitAll();
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin().loginPage("/login").permitAll()
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/home")
//                .and()
//                .logout().logoutSuccessUrl("/login?logout").permitAll()
//                .and()
//                .exceptionHandling().accessDeniedPage("/unauthorized");
    }
}
