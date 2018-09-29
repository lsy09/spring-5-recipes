package com.springrecipes.ch_09.recipe_9.vehicle.config;

import com.mysql.jdbc.Driver;
import com.springrecipes.ch_09.recipe_9.vehicle.PlainJdbcVehicleDao;
import com.springrecipes.ch_09.recipe_9.vehicle.VehicleDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class VehicleConfiguration {

    @Bean
    public VehicleDao vehicleDao(){
        return new PlainJdbcVehicleDao(dataSource());
    }

    @Bean
    public DataSource dataSource(){
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriverClass(Driver.class);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring_5_recipe");
        dataSource.setUsername("root");
        dataSource.setPassword("tnwk09");
        return dataSource;
    }


}
