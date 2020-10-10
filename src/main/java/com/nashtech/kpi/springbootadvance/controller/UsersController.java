package com.nashtech.kpi.springbootadvance.controller;

import com.nashtech.kpi.springbootadvance.entity.Users;
import com.nashtech.kpi.springbootadvance.exception.error.UsersException;
import com.nashtech.kpi.springbootadvance.model.CarsModel;
import com.nashtech.kpi.springbootadvance.model.UsersModel;
import com.nashtech.kpi.springbootadvance.model.response.ResultMessage;
import com.nashtech.kpi.springbootadvance.service.UsersService;
import com.nashtech.kpi.springbootadvance.utilities.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@EnableResourceServer
@RequestMapping("/api/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private Constants constants;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Find All Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found All Users",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Users is Empty",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Role Admin Only",
                    content = @Content)})
    @GetMapping("/listAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UsersModel>> findAllUser() {
        List<Users> listUser = usersService.findAll();
        if(listUser.isEmpty()) {
            throw new UsersException("Users is empty!!!");
        }
        List<UsersModel> result = Arrays.asList(modelMapper.map(listUser, UsersModel[].class));
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Find User By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found User",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Need Access Key Token",
                    content = @Content)})
    @GetMapping("/findUser/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<UsersModel> findUserById(@PathVariable(name = "id") Long id) {
        UsersModel userFound = usersService.findUserById(id).toModel();
        return ResponseEntity.ok().eTag(String.valueOf(userFound.getVersion())).body(userFound);
    }

    @Operation(summary = "Update User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found User",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Need Access Key Token",
                    content = @Content)})
    @GetMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<UsersModel> updateUser(@RequestBody UsersModel usersModel,
                                                   @RequestHeader("If-Match") String eTag) {
        if(eTag.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Long version = usersService.findUserByUserName(usersModel.getUsername()).getVersion();
        if (!eTag.equals(version)) {
            throw new UsersException("ETag old version");
        }
        Users userFound = usersService.updateUserInfo(usersModel.toEntity());
        if(userFound == null) {
            return new ResponseEntity<>(userFound.toModel(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userFound.toModel(), HttpStatus.OK);
        }
    }

    @Operation(summary = "Delete User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found User",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Only Role Admin can do",
                    content = @Content)})
    @GetMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        usersService.deleteUser(id);
        return new ResponseEntity<>(String.format("User Id %s has been deleted", id),HttpStatus.OK);
    }

    @Operation(summary = "Add New User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarsModel.class))}),
            @ApiResponse(responseCode = "400", description = "Bad URL Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Need Access Key",
                    content = @Content)})
    @GetMapping("/addNewUser")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER)")
    public ResponseEntity<UsersModel> addNewUser(@RequestBody UsersModel usersModel) {
        Users users = usersService.addNewUsers(usersModel.toEntity());
        return new ResponseEntity<>(users.toModel(),HttpStatus.CREATED);
    }
}
