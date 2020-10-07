package com.nashtech.kpi.springbootadvance.model;

import com.nashtech.kpi.springbootadvance.entity.Cars;
import com.nashtech.kpi.springbootadvance.entity.Colors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarsModel {

    private String id;
    private String name;
    private String model;
    private String brand;
    private String type;
    private String seat;
    private String vehicleLicense;
    private String code;
    private long cost;
    private Timestamp createdDate;

    public Cars toEntity(){
        return Cars.builder().id(id)
                .name(name)
                .model(model)
                .brand(brand)
                .type(type)
                .seat(seat)
                .vehicleLicense(vehicleLicense)
                .code(code)
                .cost(cost)
                .createdDate(createdDate).build();
    }

}
