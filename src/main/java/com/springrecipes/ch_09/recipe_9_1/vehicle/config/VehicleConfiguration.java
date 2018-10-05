package com.springrecipes.ch_09.recipe_9_1.vehicle.config;

import com.mysql.jdbc.Driver;
import com.springrecipes.ch_09.recipe_9_1.vehicle.JdbcVehicleDao;
import com.springrecipes.ch_09.recipe_9_1.vehicle.VehicleDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class VehicleConfiguration {

    @Bean
    public VehicleDao vehicleDao(){
        return new JdbcVehicleDao(dataSource());
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl("jdbc:mysql://10.10.10.12:3306/test");
        dataSource.setUsername("pikiadmin");
        dataSource.setPassword("roqkfvlzl$#2");
        return dataSource;
    }
}


