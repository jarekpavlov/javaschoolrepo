package com.jschool.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // super.configure(auth);
        auth.inMemoryAuthentication()
                .passwordEncoder(getPasswordEncoder())
                .withUser("ya@ya")
                .password(getPasswordEncoder().encode("asdf"))
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);

        http
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
