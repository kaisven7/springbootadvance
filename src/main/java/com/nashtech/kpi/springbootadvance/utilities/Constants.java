package com.nashtech.kpi.springbootadvance.utilities;

import com.nashtech.kpi.springbootadvance.model.response.ResultMessage;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static final String ERROR = "Error";
    public static final String UPDATED = "Updated";
    public static final String CREATED = "Created";
    public static final String DELETED = "Deleted";

    //Resource Server Config
    public static final String RESOURCE_ID = "resource-server-rest-api";
    public static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    public static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    public static final String SECURED_PATTERN = "/secured/**";

    public static ResultMessage created(String carName,
                                 String model,
                                 String brand,
                                 String vehicleLicense){
        return ResultMessage.builder()
                .carName(carName)
                .model(model)
                .brand(brand)
                .vehicleLicense(vehicleLicense)
                .message("New Car Added!!!")
                .statusCode(CREATED).build();
    }

    public static ResultMessage failed(){
        return ResultMessage.builder()
                .carName(null)
                .message("Something Wrong!!!")
                .statusCode(ERROR).build();
    }

    public static ResultMessage updated(String carName,
                                 String model,
                                 String brand,
                                 String vehicleLicense){
        return ResultMessage.builder()
                .carName(carName)
                .model(model)
                .brand(brand)
                .vehicleLicense(vehicleLicense)
                .message("Info of Car: ".concat(carName).concat("Has Been Updated"))
                .statusCode(UPDATED).build();
    }

    public static ResultMessage deleted(String carName,
                                 String model,
                                 String brand,
                                 String vehicleLicense){
        return ResultMessage.builder()
                .carName(carName)
                .model(model)
                .brand(brand).vehicleLicense(vehicleLicense)
                .message("This Car ".concat(carName + " ").concat("Has been deleted!"))
                .statusCode(DELETED).build();
    }
}
