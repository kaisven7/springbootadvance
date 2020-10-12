package com.nashtech.kpi.springbootadvance.controller;

import com.nashtech.kpi.springbootadvance.entity.Cars;
import com.nashtech.kpi.springbootadvance.exception.error.CarsException;
import com.nashtech.kpi.springbootadvance.model.CarsModel;
import com.nashtech.kpi.springbootadvance.model.response.ResultMessage;
import com.nashtech.kpi.springbootadvance.service.CarsService;
import com.nashtech.kpi.springbootadvance.utilities.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/Car")
public class CarsController {

    @Autowired
    private CarsService carsService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ShallowEtagHeaderFilter shallowEtagHeaderFilter;

    @Operation(summary = "Find All Cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found All Cars",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "List Cars is Empty",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Need Access Token",
                    content = @Content)})
    @GetMapping("/listAll")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<List<CarsModel>> findAllCars() {
        List<Cars> carsList = carsService.findAllCars();
        if (carsList.isEmpty())
            throw new CarsException("List Cars is Empty");
        List<CarsModel> result = Arrays.asList(modelMapper.map(carsList, CarsModel[].class));
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Create New Car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Input is Empty",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Need Access Token",
                    content = @Content)})
    @PostMapping("/addNewCar")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<ResultMessage> addNewCar(@RequestBody CarsModel carsModel) {
        Cars temp = carsService.addNewCars(carsModel.toEntity());
        if (temp.equals(null)) {
            ResultMessage result = Constants.failed();
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            ResultMessage result = Constants.created(temp.getName(),
                    temp.getModel(),
                    temp.getBrand(),
                    temp.getVehicleLicense());
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
    }

    @Operation(summary = "Find Cars By Brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found All Cars With Brand",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found Any Car",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Need Access Token",
                    content = @Content)})
    @GetMapping("/listCars/{brand}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<List<CarsModel>> findCarsByBrand(@PathVariable(name = "brand") String brand) {
        List<Cars> carsList = carsService.findCarsByBrand(brand);
        List<CarsModel> result = Arrays.asList(modelMapper.map(carsList, CarsModel[].class));
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Finding list Cars By Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found List Cars",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cars Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Need Access Token",
                    content = @Content)})
    @GetMapping("/findCarsBy/{name}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<List<CarsModel>> findingCar(@PathVariable(name = "name") String carName) {
        List<Cars> cars = carsService.findCar(carName);
        List<CarsModel> result = Arrays.asList(modelMapper.map(cars, CarsModel[].class));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Finding Car With Name And Color")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found List Cars",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cars Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Need Access Token",
                    content = @Content)})
    @GetMapping("/findCarsBy")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<CarsModel> findingCarByNameAndColor(@RequestParam(name = "name") String carName,
                                                              @RequestParam(name = "color") String color) {
        CarsModel result = carsService.findCarWithNameAndColor(carName, color).toModel();
        return ResponseEntity.ok().eTag(String.valueOf(result.getVersion())).body(result);
    }

    @Operation(summary = "Update Info Car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Info Car",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Car Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Need Access Token",
                    content = @Content)})
    @PatchMapping("/updateCar")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<ResultMessage> updateCar(@RequestBody CarsModel carsModel,
                                                   @RequestHeader("If-Match") String eTag) {
        if (eTag.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Long version = carsService.findByNameAndVehicleLicense(carsModel.getName(),
                carsModel.getVehicleLicense()).getVersion();
        if (!eTag.equals(version.toString())) {
            ResultMessage result = Constants.failed();
            return new ResponseEntity<>(result, HttpStatus.PRECONDITION_FAILED);
        }
        Cars temp = carsService.updateCar(carsModel.toEntity());
        if (temp.equals(null)) {
            ResultMessage result = Constants.failed();
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            ResultMessage result = Constants.updated(temp.getName(),
                    temp.getModel(),
                    temp.getBrand(),
                    temp.getVehicleLicense());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @Operation(summary = "Delete Car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete Car",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Car Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Only Admin Can Access",
                    content = @Content)})
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResultMessage> removeCar(@PathVariable(name = "id") Long id) {
        Cars deletedCar = carsService.deleteCar(id);
        ResultMessage result = Constants.deleted(deletedCar.getName(),
                deletedCar.getModel(),
                deletedCar.getBrand(),
                deletedCar.getVehicleLicense());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
