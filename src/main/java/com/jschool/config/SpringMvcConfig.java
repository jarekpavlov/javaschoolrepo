package com.jschool.config;

import com.jschool.DAO.ClientDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.jschool")
public class SpringMvcConfig implements WebMvcConfigurer {

    @Bean
    public ViewResolver getViewResolver(){

        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;

    }
    @Bean
    public ClientDaoImpl getClientDao(){
        return new ClientDaoImpl();
    }


}
