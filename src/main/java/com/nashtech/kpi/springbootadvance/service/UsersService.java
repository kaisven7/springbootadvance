package com.nashtech.kpi.springbootadvance.service;

import com.nashtech.kpi.springbootadvance.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;



}
