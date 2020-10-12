package com.nashtech.kpi.springbootadvance.service;

import com.nashtech.kpi.springbootadvance.entity.Cars;
import com.nashtech.kpi.springbootadvance.exception.error.CarsException;
import com.nashtech.kpi.springbootadvance.repository.CarsRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit Testing Service Level Using Mockito (Mock and Inject Mock) - Cars Service Level
 * This file will test with happy cases and error cases also has exception cases
 **/

public class CarsServiceTest {

    @InjectMocks
    private CarsService carsService;

    @Mock
    private CarsRepository carsRepository;

    @Mock
    private Cars cars;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        carsService = new CarsService();
        carsService.setCarsRepository(carsRepository);
    }

    //Testing first time for Mock
    @Test
    public void testMockCreation() {
        assertNotNull(carsService);
        assertNotNull(carsRepository);
        assertNotNull(cars);
    }

    private List<Cars> prepareData() {
        List<Cars> carsList = new ArrayList<>();
        carsList.add(Cars.builder().id(1)
                .name("testCar")
                .model("9999")
                .brand("testBrand")
                .type("typeDemo")
                .seat(7)
                .vehicleLicense("TEST25687TS")
                .cost(30000L)
                .color("Red")
                .version(1)
                .createdDate(null).build());
        carsList.add(Cars.builder().id(3)
                .name("testCar")
                .model("8888")
                .brand("testBrand")
                .type("typeDemo")
                .seat(7)
                .vehicleLicense("TEST92571TS")
                .cost(20000L)
                .color("White")
                .version(1)
                .createdDate(null).build());
        carsList.add(Cars.builder().id(2)
                .name("demoCar")
                .model("2222")
                .brand("testBrand")
                .type("typeDemo")
                .seat(5)
                .vehicleLicense("TEST11111TS")
                .cost(32000L)
                .color("Blue")
                .version(1)
                .createdDate(null).build());
        return carsList;
    }

    @Test
    public void testFindAll() {
        when(carsRepository.findAll()).thenReturn(prepareData());
        List<Cars> result = carsService.findAllCars();
        verify(carsRepository, atLeastOnce()).findAll();

        assertNotNull(result);
        assertEquals(prepareData(),result);
    }

    @Test
    public void testFindAll_ButEmpty() {
        when(carsRepository.findAll()).thenReturn(Lists.emptyList());
        List<Cars> result = carsService.findAllCars();
        verify(carsRepository, atLeastOnce()).findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testAddNewCar() {
        Cars cars = Cars.builder().id(1)
                .name("testCar")
                .model("9999")
                .brand("testBrand")
                .type("typeDemo")
                .seat(7)
                .vehicleLicense("TEST25687TS")
                .cost(30000L)
                .color("Red")
                .version(1)
                .createdDate(Timestamp.valueOf(LocalDateTime.now())).build();

        when(carsRepository.findByNameAndVehicleLicense(cars.getName(),cars.getVehicleLicense()))
                .thenReturn(Optional.empty());
        when(carsRepository.save(cars)).thenReturn(cars);
        Cars result = carsService.addNewCars(cars);
        verify(carsRepository,atLeastOnce()).save(cars);

        assertNotNull(result);
        assertEquals(cars,result);
    }

    @Test
    public void testAddNewCar_ButError() {
        Cars cars = Cars.builder().id(1)
                .name("testCar")
                .model("9999")
                .brand("testBrand")
                .type("typeDemo")
                .seat(7)
                .vehicleLicense("TEST25687TS")
                .cost(30000L)
                .color("Red")
                .version(1)
                .createdDate(Timestamp.valueOf(LocalDateTime.now())).build();

        Exception exception = assertThrows(CarsException.class,() -> {
            when(carsRepository.findByNameAndVehicleLicense(cars.getName(),cars.getVehicleLicense()))
                    .thenReturn(Optional.of(cars));
            carsService.addNewCars(cars);
            verify(carsRepository,atLeastOnce()).save(cars);
        });

        String expectedMess = String.format("This Car %s and Car License %s already Exists!",
                cars.getName(),cars.getVehicleLicense());
        String actualMess = exception.getMessage();

        assertThat(actualMess).isEqualTo(expectedMess);
    }

    @Test
    public void testFindCar() {
        String carName = "testCar";
        when(carsRepository.findByName(carName)).thenReturn(prepareData()
                .stream().filter(s -> s.getName().equals(carName))
                .collect(Collectors.toList()));
        List<Cars> carsList = carsService.findCar(carName);
        verify(carsRepository,atLeastOnce()).findByName(carName);

        assertNotNull(carsList);
        assertEquals(prepareData().stream().filter(s -> s.getName().equals(carName))
                .collect(Collectors.toList()),carsList);
    }

    @Test
    public void testFindCar_ButNotFound() {
        String carName = "testCar";
        Exception exception = assertThrows(CarsException.class,() -> {
            when(carsRepository.findByName(carName))
                    .thenReturn(Lists.emptyList());
            carsService.findCar(carName);
            verify(carsRepository,atLeastOnce()).findByName(carName);
        });

        String expectedMess = String.format("Car %s not found !!!", carName);
        String actualMess = exception.getMessage();

        assertThat(actualMess).isEqualTo(expectedMess);
    }

    @Test
    public void testUpdateCar() {
        Cars car = Cars.builder().id(1)
                .name("testCar")
                .model("9999")
                .brand("testBrand")
                .type("typeDemo")
                .seat(7)
                .vehicleLicense("TEST25687TS")
                .cost(30000L)
                .color("Red")
                .version(1)
                .createdDate(Timestamp.valueOf(LocalDateTime.now())).build();
        Cars updateCar = Cars.builder().id(1)
                .name("testCar")
                .model("9977")
                .brand("testBrand")
                .type("typeDemo")
                .seat(5)
                .vehicleLicense("TEST25687TS")
                .cost(25000L)
                .color("Gray")
                .version(1)
                .createdDate(Timestamp.valueOf(LocalDateTime.now())).build();
        when(carsRepository.findByNameAndVehicleLicense(car.getName(),car.getVehicleLicense()))
                .thenReturn(Optional.of(car));
        when(carsRepository.save(any(Cars.class))).thenReturn(updateCar);
        Cars result = carsService.updateCar(updateCar);

        assertNotNull(result);
        assertEquals(updateCar,result);
        assertEquals(updateCar.getModel(),result.getModel());
        assertEquals(updateCar.getSeat(),result.getSeat());
        assertEquals(updateCar.getCost(),result.getCost());
    }

    @Test
    public void testUpdateCar_ButNotFound() {
        Cars car = Cars.builder().id(1)
                .name("testCar")
                .model("9999")
                .brand("testBrand")
                .type("typeDemo")
                .seat(7)
                .vehicleLicense("TEST25687TS")
                .cost(30000L)
                .color("Red")
                .version(1)
                .createdDate(Timestamp.valueOf(LocalDateTime.now())).build();

        Exception exception = assertThrows(CarsException.class,() -> {
            when(carsRepository.findByNameAndVehicleLicense(car.getName(), car.getVehicleLicense())).thenReturn(Optional.empty());
            carsService.updateCar(car);
        });

        String expectedMess = String.format("Car %s not found !!!", car.getName());
        String actualMess = exception.getMessage();

        assertTrue(actualMess.contains(expectedMess));
    }

    @Test
    public void testDeleteCar() {
        Long id = 1L;
        Cars car = Cars.builder().id(1)
                .name("testCar")
                .model("9999")
                .brand("testBrand")
                .type("typeDemo")
                .seat(7)
                .vehicleLicense("TEST25687TS")
                .cost(30000L)
                .color("Red")
                .version(1)
                .createdDate(Timestamp.valueOf(LocalDateTime.now())).build();

        when(carsRepository.findById(id)).thenReturn(Optional.of(car));
        carsService.deleteCar(id);
        verify(carsRepository,atLeastOnce()).delete(car);

        assertThat(car == null);
    }
}
