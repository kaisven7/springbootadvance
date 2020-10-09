package com.nashtech.kpi.springbootadvance.repository;

import com.nashtech.kpi.springbootadvance.entity.Cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarsRepository extends JpaRepository<Cars,String> {

    List<Cars> findByBrand (String brand);

    List<Cars> findByName (String name);

    Optional<Cars> findByNameAndColor (String name, String color);

    Optional<Cars> findByNameAndVehicleLicense (String name, String vehicleLicense);

    Optional<Cars> findById(Long id);

    @Query(value = " SELECT COUNT(ID) FROM CARS", nativeQuery = true)
    Long countId();

}
