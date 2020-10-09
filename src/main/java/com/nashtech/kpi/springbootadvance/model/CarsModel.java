package com.nashtech.kpi.springbootadvance.model;

import com.nashtech.kpi.springbootadvance.entity.Cars;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarsModel{

    private long id;
    private String name;
    private String model;
    private String brand;
    private String type;
    private Integer seat;
    private String vehicleLicense;
    private Long cost;
    private String color;
    private Timestamp createdDate;
    private long version;

    public Cars toEntity(){
        return Cars.builder().id(id)
                .name(name)
                .model(model)
                .brand(brand)
                .type(type)
                .seat(seat)
                .vehicleLicense(vehicleLicense)
                .cost(cost)
                .color(color)
                .version(version)
                .createdDate(createdDate).build();
    }
}
