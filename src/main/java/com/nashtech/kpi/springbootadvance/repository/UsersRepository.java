package com.nashtech.kpi.springbootadvance.repository;

import com.nashtech.kpi.springbootadvance.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

    Users findByUsername (String username);

}
