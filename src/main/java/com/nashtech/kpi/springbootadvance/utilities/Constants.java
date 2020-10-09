package com.nashtech.kpi.springbootadvance.utilities;

import com.nashtech.kpi.springbootadvance.model.response.ResultMessage;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static final String ERROR = "Error";
    public static final String UPDATED = "Updated";
    public static final String CREATED = "Created";
    public static final String DELETED = "Deleted";

    public ResultMessage created(String carName,
                                 String model,
                                 String brand,
                                 String vehicleLicense){
        return ResultMessage.builder()
                .carName(carName)
                .model(model)
                .brand(brand)
                .vehicleLicense(vehicleLicense)
                .message("New Car Added!!!")
                .statusCode(this.CREATED).build();
    }

    public ResultMessage failed(){
        return ResultMessage.builder()
                .carName(null)
                .message("Something Wrong!!!")
                .statusCode(this.ERROR).build();
    }

    public ResultMessage updated(String carName,
                                 String model,
                                 String brand,
                                 String vehicleLicense){
        return ResultMessage.builder()
                .carName(carName)
                .model(model)
                .brand(brand)
                .vehicleLicense(vehicleLicense)
                .message("Info of Car: ".concat(carName).concat("Has Been Updated"))
                .statusCode(this.UPDATED).build();
    }

    public ResultMessage deleted(String carName,
                                 String model,
                                 String brand,
                                 String vehicleLicense){
        return ResultMessage.builder()
                .carName(carName)
                .model(model)
                .brand(brand).vehicleLicense(vehicleLicense)
                .message("This Car ".concat(carName + " ").concat("Has been deleted!"))
                .statusCode(this.DELETED).build();
    }
}
