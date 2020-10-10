package com.nashtech.kpi.springbootadvance.service;

import com.nashtech.kpi.springbootadvance.entity.Users;
import com.nashtech.kpi.springbootadvance.exception.error.UsersException;
import com.nashtech.kpi.springbootadvance.repository.UsersRepository;
import com.nashtech.kpi.springbootadvance.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Utilities utils;

    public UsersRepository getUsersRepository() {
        return usersRepository;
    }

    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> findAll (){
        return usersRepository.findAll();
    }

    public Users findUserByUserName (String username) {
        return usersRepository.findByUsername(username).orElseThrow(
                () -> new UsersException(String.format("User %s not found!!!",username)));
    }

    public Users findUserById (Long id) {
        return usersRepository.findById(id).orElseThrow(
                () -> new UsersException(String.format("User not found By Id %s !!!",id)));
    }

    public Users addNewUsers (Users users) {
        Long idFound = usersRepository.countId();
        Users checkUser = usersRepository.findByUsername(users.getUsername()).orElseThrow(
                () -> new UsersException(String.format("User %s already Existed!", users.getUsername())));
        users.setId(idFound + 1);
        users.setRole("ROLE_USER"); // create New user will default role USER
        users.setVersion(1);
        return usersRepository.save(users);
    }

    public Users updateUserInfo (Users users) {
        return usersRepository.findById(users.getId()).map(updateUser -> {
            updateUser.setUsername(utils.checkValue(updateUser.getUsername(),users.getUsername()).toString());
            updateUser.setPassword(passwordEncoder.encode(users.getPassword())); // need to change new password each update
            if(users.getRole().equals(updateUser)
            && users.getRole().equals("ROLE_ADMIN")
            && updateUser.getRole().equals("ROLE_ADMIN")) {
                updateUser.setRole(users.getRole());
            }
            return usersRepository.save(updateUser);
        }).orElseThrow(() ->  new UsersException(String.format("User %s not found!!!",users.getUsername())));
    }

    public Users deleteUser (Long id) {
        Users userFound = usersRepository.findById(id).orElseThrow(
                () -> new UsersException(String.format("User not found By Id %s !!!",id)));
        usersRepository.delete(userFound);
        return userFound;
    }
}
