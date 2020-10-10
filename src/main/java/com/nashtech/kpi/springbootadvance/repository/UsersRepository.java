package com.nashtech.kpi.springbootadvance.repository;

import com.nashtech.kpi.springbootadvance.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByUsername (String username);

    @Query(value = " SELECT COUNT(ID) FROM USERS", nativeQuery = true)
    Long countId();
}
