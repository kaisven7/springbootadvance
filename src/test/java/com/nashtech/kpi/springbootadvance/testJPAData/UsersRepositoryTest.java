package com.nashtech.kpi.springbootadvance.testJPAData;

import com.nashtech.kpi.springbootadvance.entity.Users;
import com.nashtech.kpi.springbootadvance.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testFindByUserName_Repository() {
        String username = "admin";
        Users result = usersRepository.findByUsername(username).get();

        assertNotNull(result);
        assertEquals(username,result.getUsername());
    }

    @Test
    public void testCountId_Repository() {
        Long count = 2L;
        Long result = usersRepository.countId();

        assertThat(count).isEqualTo(result);
    }
}
