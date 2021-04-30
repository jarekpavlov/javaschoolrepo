package com.jschool.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // super.configure(auth);
        auth.inMemoryAuthentication()
                .withUser("ya@ya")
                .password("asdf")
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);

        http.csrf().disable()
                .authorizeRequests()
                  .antMatchers("/").permitAll()
                  .antMatchers("/users/**").permitAll()
                  .anyRequest().hasRole("USER").and()
                .formLogin()
                  .loginPage("/login")
                  .permitAll()
                  .and()
                .logout()
                  .logoutUrl("/logout")
                  .permitAll();
    }
}
