package com.jschool.config;

import com.jschool.DAO.EntityDaoImpl;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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
    public EntityDaoImpl getClientDao(){
        return new EntityDaoImpl(getSessionFactory());
    }

    @Bean
    public StandardServiceRegistry getStandardServiceRegistry(){
        return new StandardServiceRegistryBuilder().configure().build();
    }

    @Bean
    public SessionFactory getSessionFactory(){
        return new MetadataSources(getStandardServiceRegistry())
                .buildMetadata().buildSessionFactory();
    }

}
