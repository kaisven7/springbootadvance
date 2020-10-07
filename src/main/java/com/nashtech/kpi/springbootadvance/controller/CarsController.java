package com.nashtech.kpi.springbootadvance.controller;

import com.nashtech.kpi.springbootadvance.entity.Cars;
import com.nashtech.kpi.springbootadvance.exception.error.CarsException;
import com.nashtech.kpi.springbootadvance.model.CarsModel;
import com.nashtech.kpi.springbootadvance.model.response.ResultMessage;
import com.nashtech.kpi.springbootadvance.service.CarsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/Car")
public class CarsController {

    @Autowired
    private CarsService carsService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Find All Cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found All Cars",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "List Cars is Empty",
                    content = @Content) })
    @GetMapping("/listAll")
    public ResponseEntity<List<CarsModel>> findAllCars() {
        List<Cars> carsList = carsService.findAllCars();
        if(carsList.isEmpty())
            throw new CarsException("List Cars is Empty");
        List<CarsModel> result = Arrays.asList(modelMapper.map(carsList,CarsModel[].class));
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Create New Car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Input is Empty",
                    content = @Content) })
    @PostMapping("/addNewCar")
    public ResponseEntity<ResultMessage> addNewCar (@RequestBody CarsModel carsModel) {
        ResultMessage result = new ResultMessage();
        return new ResponseEntity<ResultMessage>(result, HttpStatus.CREATED);
    }

}
