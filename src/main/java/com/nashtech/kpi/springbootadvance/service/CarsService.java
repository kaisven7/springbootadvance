package com.nashtech.kpi.springbootadvance.service;

import com.nashtech.kpi.springbootadvance.entity.Cars;
import com.nashtech.kpi.springbootadvance.repository.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarsService {

    @Autowired
    private CarsRepository carsRepository;

    public Cars addNewCars (Cars cars) {
        UUID uuid = UUID.randomUUID();
        cars.setId(uuid.toString());
        return carsRepository.save(cars);
    }

    public List<Cars> findAllCars() {
        return carsRepository.findAll();
    }

    public List<Cars> findCarsByBrand (String brand) {
        return carsRepository.findByBrand(brand);
    }

}
