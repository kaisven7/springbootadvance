package com.nashtech.kpi.springbootadvance.testJPAData;

import com.nashtech.kpi.springbootadvance.entity.Cars;
import com.nashtech.kpi.springbootadvance.repository.CarsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CarsRepositoryTest {

    @Autowired
    private CarsRepository carsRepository;

    @Test
    public void testFindByBrand_Repository() {
        String brand = "Toyota";
        List<Cars> result = carsRepository.findByBrand(brand);;

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testFindByName_Repository() {
        String name = "Fortuner";
        List<Cars> result = carsRepository.findByName(name);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testFindByNameAndColor_Repository() {
        String name = "M3";
        String color = "Black";
        Cars result = carsRepository.findByNameAndColor(name,color).get();

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(name,result.getName());
        assertEquals(color,result.getColor());
    }

    @Test
    public void testFindByNameAndVehicleLicense_Repository() {
        String name = "M3";
        String carLicense = "M3BM23187BM";
        Cars result = carsRepository.findByNameAndVehicleLicense(name,carLicense).get();

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(name,result.getName());
        assertEquals(carLicense,result.getVehicleLicense());
    }

    @Test
    public void testCountId_Repository() {
        Long count = 5L;
        Long result = carsRepository.countId();

        assertThat(count).isEqualTo(result);
    }
}
