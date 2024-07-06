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
    void setUp(){
        underTest = new UserService(userRepository);
    }

    @Test
    void getUsers(){
        //Dado
        User user1 = new User("User1","user1@mail.com");
        User user2 = new User("User2","user2@mail.com");

        List<User> users = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        //Cuando
        List<User> result = underTest.getUsers();

        //Entonces
        verify(userRepository, Mockito.times(2)).findAll();
        assertEquals(users, result);
    }

    @Test 
    void addUser() {
        // Dado
        User user = new User("Sample User 1", "samplemail1@mail.com");

        // Cuando 
        underTest.addUser(user);

        // Entonces 
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertEquals(user, capturedUser);
    }
}
