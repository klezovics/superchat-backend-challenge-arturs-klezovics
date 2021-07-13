package com.klezovich.superchat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@Profile("!no_security")
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser("spring")
                .password(encoder.encode("secret"))
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity)
            throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/h2-console/**")
                .permitAll()
                .antMatchers("/webhook/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        httpSecurity.csrf().disable();
        httpSecurity.cors().disable();
    }
}