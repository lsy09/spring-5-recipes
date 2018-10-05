package com.springrecipes.ch_09.recipe_9_1.vehicle;

import com.springrecipes.ch_09.recipe_9_1.vehicle.config.VehicleConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(VehicleConfiguration.class);

        VehicleDao vehicleDao = context.getBean(VehicleDao.class);

        /**
         * 9_1_i ~ 9_1_iv
         */
//        Vehicle vehicle = new Vehicle("TEM0018", "Red", 4, 4);
//        vehicleDao.insert(vehicle);
//
//        vehicle = vehicleDao.findByVehicleNo("TEM0018");
//        System.out.println("Vehicle No: " + vehicle.getVehicleNo());
//        System.out.println("Color: " + vehicle.getColor());
//        System.out.println("Wheel: " + vehicle.getWheel());
//        System.out.println("Seat: " + vehicle.getSeat());


        /**
         * 9_1_v
         */
        Vehicle vehicle1 = new Vehicle("TEM0022", "Blue", 4, 4);
        Vehicle vehicle2 = new Vehicle("TEM0023", "Black", 4, 6);
        Vehicle vehicle3 = new Vehicle("TEM0024", "Green", 4, 5);
        vehicleDao.insert(Arrays.asList(vehicle1, vehicle2, vehicle3));

        vehicleDao.findAll().forEach(System.out::println);
    }
}
