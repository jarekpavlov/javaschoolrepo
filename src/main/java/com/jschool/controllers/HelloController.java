package com.jschool.controllers;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class HelloController {

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String hello(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mydb?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("pit_bull");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String sql ="SELECT* from clients WHERE id=1";
        ResultSetExtractor<String> resultSetExtractor = new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                if (resultSet.next()){
                    String name = resultSet.getString("name");
                    return name;
                }
                return null;
            }
        };
        System.out.println( jdbcTemplate.query(sql,resultSetExtractor));
        return "hello";
    }
}
