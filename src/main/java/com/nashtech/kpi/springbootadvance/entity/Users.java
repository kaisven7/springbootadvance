package com.nashtech.kpi.springbootadvance.entity;

import com.nashtech.kpi.springbootadvance.model.UsersModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name= "USERS")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Version
    private long version;

    public UsersModel toModel() {
        return UsersModel.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .version(version)
                .build();
    }
}
