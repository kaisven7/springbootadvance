package com.nashtech.kpi.springbootadvance.service;

import com.nashtech.kpi.springbootadvance.entity.Cars;
import com.nashtech.kpi.springbootadvance.exception.error.CarsException;
import com.nashtech.kpi.springbootadvance.repository.CarsRepository;
import com.nashtech.kpi.springbootadvance.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarsService {

    @Autowired
    private CarsRepository carsRepository;

    public CarsRepository getCarsRepository() {
        return carsRepository;
    }

    public void setCarsRepository(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }

    public Cars addNewCars (Cars cars) {
        long foundId = carsRepository.countId();
        if(carsRepository.findByNameAndVehicleLicense(cars.getName(),cars.getVehicleLicense()).isPresent()) {
            throw new CarsException(String.format("This Car %s and Car License %s already Exists!",
                            cars.getName(),cars.getVehicleLicense()));
        }
        cars.setId(foundId + 1);
        cars.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        cars.setVersion(1);
        return carsRepository.save(cars);
    }

    public List<Cars> findAllCars() {
        return carsRepository.findAll();
    }

    public List<Cars> findCarsByBrand (String brand) {
        return carsRepository.findByBrand(brand);
    }

    public List<Cars> findCar(String carName) {
        List<Cars> result = carsRepository.findByName(carName).stream().collect(Collectors.toList());
        if(result.isEmpty())
             throw new CarsException(String.format("Car %s not found !!!", carName));
        return result;
    }

    public Cars findCarWithNameAndColor(String carName,String color) {
        Cars result = carsRepository.findByNameAndColor(carName,color).orElseThrow(
                () -> new CarsException(String.format("Car %s with Color %s not found !!!", carName, color)));
        return result;
    }

    public Cars findByNameAndVehicleLicense(String carName, String vehicleLicense) {
        return carsRepository.findByNameAndVehicleLicense(carName,vehicleLicense).orElseThrow(
                () -> new CarsException(String.format("Car %s not found !!!", carName)));
    }

    public Cars updateCar(Cars cars) {
        return carsRepository.findByNameAndVehicleLicense(cars.getName(),cars.getVehicleLicense()).map(
                updateCar -> {
                    updateCar.setName(Utilities.checkValue(updateCar.getName(),cars.getName()).toString());
                    updateCar.setModel(Utilities.checkValue(updateCar.getModel(),cars.getModel()).toString());
                    updateCar.setBrand(Utilities.checkValue(updateCar.getBrand(),cars.getBrand()).toString());
                    updateCar.setType(Utilities.checkValue(updateCar.getType(),cars.getType()).toString());
                    updateCar.setColor(Utilities.checkValue(updateCar.getColor(),cars.getColor()).toString());
                    updateCar.setSeat(Integer.parseInt(Utilities.checkValue(updateCar.getSeat(),cars.getSeat()).toString()));
                    updateCar.setCost(Long.parseLong(Utilities.checkValue(updateCar.getCost(),cars.getCost()).toString()));
                    updateCar.setVehicleLicense(Utilities.checkValue(updateCar.getVehicleLicense(),cars.getVehicleLicense()).toString());
                    updateCar.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
                    return carsRepository.save(updateCar);
                })
                .orElseThrow(() -> new CarsException(String.format("Car %s not found !!!", cars.getName())));
    }

    public Cars deleteCar(Long id) {
        Cars findingCar = carsRepository.findById(id).orElseThrow(
                () -> new CarsException(String.format("Car not found By Id %s", id)));
        carsRepository.delete(findingCar);
        return findingCar;
    }
}
