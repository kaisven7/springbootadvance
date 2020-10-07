package com.nashtech.kpi.springbootadvance.repository;

import com.nashtech.kpi.springbootadvance.entity.Cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsRepository extends JpaRepository<Cars,String> {

    List<Cars> findByBrand (String brand);

    Cars findByName (String name);

}
