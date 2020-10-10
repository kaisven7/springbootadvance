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

    private long id;
    private String username;
    private String password;
    private String role;
    private long version;

    public Users toEntity() {
        return Users.builder().id(id)
                .username(username)
                .password(password)
                .role(role)
                .version(version).build();
    }

}
