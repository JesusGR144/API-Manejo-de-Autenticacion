package com.jjgr.store_demo.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository);
    }

    @Test
    void getUsers() {
        // Dado
        UserEntity user1 = new UserEntity("User1", "1234", "user1@mail.com", true, true, true);
        UserEntity user2 = new UserEntity("User2", "1234", "user2@mail.com", true, true, true);

        List<UserEntity> users = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        // Cuando
        List<UserEntity> result = underTest.getUsers();

        // Entonces
        verify(userRepository, Mockito.times(2)).findAll();
        assertEquals(users, result);
    }

    @Test
    void addUser() {
        // Dado
        UserEntity user = new UserEntity("Sample User 1", "1234", "samplemail1@mai.com", true, true, true);

        // Cuando
        underTest.addUser(user);

        // Entonces
        ArgumentCaptor<UserEntity> userArgumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        UserEntity capturedUser = userArgumentCaptor.getValue();

        assertEquals(user, capturedUser);
    }
}
