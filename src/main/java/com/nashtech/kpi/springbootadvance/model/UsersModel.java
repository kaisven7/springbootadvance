package com.nashtech.kpi.springbootadvance.model;

import com.nashtech.kpi.springbootadvance.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersModel {

    private String username;
    private String password;

}
