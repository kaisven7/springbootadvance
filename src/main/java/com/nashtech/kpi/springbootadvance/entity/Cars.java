package com.nashtech.kpi.springbootadvance.entity;

import com.nashtech.kpi.springbootadvance.model.CarsModel;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "CARS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cars {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "SEAT")
    private String seat;

    @Column(name = "VEHICLE_LICENSE")
    private String vehicleLicense;

    @Column(name = "CODE")
    private String code;

    @Column(name = "COST")
    private long cost;

    @Column(name = "COLOR")
    private long colorId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CARS_COLORS", joinColumns = @JoinColumn(name = "CARS_COLOR")
            , inverseJoinColumns = @JoinColumn(name = "COLORS_ID"))
    private List<Colors> colors;

    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;

    public CarsModel toModel(){
        return CarsModel.builder().id(id)
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
