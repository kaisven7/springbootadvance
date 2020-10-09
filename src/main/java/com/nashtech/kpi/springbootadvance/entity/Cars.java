package com.nashtech.kpi.springbootadvance.entity;

import com.nashtech.kpi.springbootadvance.model.CarsModel;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "CARS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "SEAT")
    private Integer seat;

    @Column(name = "VEHICLE_LICENSE")
    private String vehicleLicense;

    @Column(name = "COST")
    private Long cost;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;

    @Version
    private long version;

    public CarsModel toModel(){
        return CarsModel.builder().id(id)
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
