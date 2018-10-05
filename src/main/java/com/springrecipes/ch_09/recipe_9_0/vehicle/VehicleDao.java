package com.springrecipes.ch_09.recipe_9_0.vehicle;

import java.util.List;

public interface VehicleDao {
    void insert(Vehicle vehicle);
    void insert(Iterable<Vehicle> vehicles);
    void update(Vehicle vehicle);
    void delete(Vehicle vehicle);
    Vehicle findByVehicleNo(String vehicleNo);
    List<Vehicle> findAll();
}
