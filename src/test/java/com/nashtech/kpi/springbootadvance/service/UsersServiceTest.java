package com.nashtech.kpi.springbootadvance.service;

import com.nashtech.kpi.springbootadvance.entity.Users;
import com.nashtech.kpi.springbootadvance.exception.error.UsersException;
import com.nashtech.kpi.springbootadvance.repository.UsersRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit Testing Service Level Using Mockito (Mock and Inject Mock) - Users Service Level
 * This file will test with happy cases and error cases also has exception cases
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

    //Testing first time for Mock
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
        assertEquals(setUpTestData(),result);
    }

    @Test
    public void testFindAll_ButError() {
        when(usersRepository.findAll()).thenReturn(Lists.emptyList());
        List<Users> result = usersService.findAll();
        verify(usersRepository, atLeastOnce()).findAll();

        assertTrue(result.isEmpty());
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
        assertEquals(username,result.getUsername());
    }

    @Test
    public void testFindUserByUserName_ButNotFound() {
        String username = "test";
        Users temp = Users.builder().id(1).username("test")
                .password("testing")
                .role("ROLE_TEST")
                .version(1).build();
        Exception exception = assertThrows(UsersException.class, () -> {
            when(usersRepository.findByUsername(username)).thenReturn(Optional.empty());
            usersService.findUserByUserName(username);
            verify(usersRepository, atLeastOnce()).findByUsername(username);
        });

        String expectedMess = String.format("User %s not found!!!", temp.getUsername());
        String actualMess = exception.getMessage();

        assertTrue(actualMess.contains(expectedMess));
    }

    @Test
    public void testAddNewUser() {
        String username = "test";
        Users temp = Users.builder().id(1).username("test")
                .password("testing")
                .role("ROLE_TEST")
                .version(1).build();
        when(usersRepository.countId()).thenReturn(0L);
        when(usersRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(usersRepository.save(temp)).thenReturn(temp);
        Users result = usersService.addNewUser(temp);

        assertNotNull(result);
        assertEquals(1,result.getId());
    }

    @Test
    public void tryToAddNewUser_ButError() {
        String username = "test";
        Users temp = Users.builder().id(1).username("test")
                .password("testing")
                .role("ROLE_TEST")
                .version(1).build();

        Exception exception = assertThrows(UsersException.class, () -> {
            when(usersRepository.countId()).thenReturn(0L);
            when(usersRepository.findByUsername(username)).thenReturn(Optional.of(temp));
            usersService.addNewUser(temp);
        });

        String expectedMess = String.format("User %s already Existed!", temp.getUsername());
        String actualMess = exception.getMessage();

        assertTrue(actualMess.contains(expectedMess));
    }

    @Test
    public void TestUpdateUser() {
        Users temp = Users.builder().id(1).username("test")
                .password("testing")
                .role("ROLE_TEST")
                .version(1).build();
        Users update = Users.builder().id(1).username("test")
                .password("testing123") // new password
                .role("ROLE_ADMIN")
                .version(1).build();

        when(usersRepository.findById(1L)).thenReturn(Optional.of(temp));
        when(usersRepository.save(any(Users.class))).thenReturn(update);
        Users result = usersService.updateUserInfo(update);

        assertEquals(update,result);
        assertEquals(update.getPassword(),result.getPassword());
    }

    @Test
    public void TestUpdateUser_ButNotFound() {
        Users temp = Users.builder().id(1).username("test")
                .password("testing")
                .role("ROLE_TEST")
                .version(1).build();

        Exception exception = assertThrows(UsersException.class, () -> {
            when(usersRepository.findById(1L)).thenReturn(Optional.empty());
            usersService.updateUserInfo(temp);
        });

        String expectedMess = String.format("User %s not found!!!", temp.getUsername());
        String actualMess = exception.getMessage();

        assertTrue(actualMess.contains(expectedMess));
    }

    @Test
    public void TestDeleteUser() {
        Long id = 1L;
        Users temp = Users.builder().id(1).username("test")
                .password("testing")
                .role("ROLE_TEST")
                .version(1).build();
        when(usersRepository.findById(id)).thenReturn(Optional.of(temp));
        usersService.deleteUser(id);
        verify(usersRepository,times(1)).delete(temp);

        assertThat(temp == null);

    }

    @Test
    public void TestDeleteUser_ButNotFounđ() {
        Long id = 1L;
        Users temp = Users.builder().id(1).username("test")
                .password("testing")
                .role("ROLE_TEST")
                .version(1).build();

        Exception exception = assertThrows(UsersException.class, () -> {
            when(usersRepository.findById(id)).thenReturn(Optional.empty());
            usersService.deleteUser(id);
        });

        String expectedMess = String.format("User not found By Id %s !!!", id);
        String actualMess = exception.getMessage();

        assertTrue(actualMess.contains(expectedMess));
    }
}
