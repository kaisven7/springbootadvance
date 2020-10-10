package com.nashtech.kpi.springbootadvance.service;

import com.nashtech.kpi.springbootadvance.entity.Users;
import com.nashtech.kpi.springbootadvance.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit Testing Service Level Using Mockito (Mock and Inject Mock)
 **/

public class UsersServiceTest {

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private Users users;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        usersService = new UsersService();
        usersService.setUsersRepository(usersRepository);
    }

    @Test
    public void testMockCreation() {
        assertNotNull(usersService);
        assertNotNull(usersRepository);
        assertNotNull(users);
    }

    private List<Users> setUpTestData() {
        List<Users> initUsers = new ArrayList<>();
        initUsers.add(Users.builder().id(1).username("test")
                .password("testing")
                .role("ROLE_TEST")
                .version(1).build());
        initUsers.add(Users.builder().id(2).username("test1")
                .password("testing123")
                .role("ROLE_TEST")
                .version(1).build());
        return initUsers;
    }

    @Test
    public void testFindAll() {
        when(usersRepository.findAll()).thenReturn(setUpTestData());
        List<Users> result = usersService.findAll();
        verify(usersRepository, atLeastOnce()).findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testFindUserByUserName() {
        String username = "test";
        Users temp = Users.builder().id(1).username("test")
                .password("testing")
                .role("ROLE_TEST")
                .version(1).build();
        when(usersRepository.findByUsername(username)).thenReturn(Optional.of(temp));
        Users result = usersService.findUserByUserName(username);
        verify(usersRepository, atLeastOnce()).findByUsername(username);

        assertNotNull(result);
        assertTrue(result.getUsername().equals(username));
    }

    @Test
    public void testAddNewUser() {
        Long id = 1L;
        Users temp = Users.builder().id(1).username("test")
                .password("testing")
                .role("ROLE_TEST")
                .version(1).build();
        when(usersRepository.countId()).thenReturn(id);
        when(usersRepository.findById(1L)).thenReturn(Optional.of(temp));
        when(usersRepository.save(temp)).thenReturn(temp);
        Users result = usersService.addNewUsers(temp);
        assertNotNull(result);
        assertEquals(1,result.getId());
    }
}
