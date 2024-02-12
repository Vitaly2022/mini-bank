package com.vit.minibank.service.user;

import com.vit.minibank.domain.entity.User;
import com.vit.minibank.domain.exceptions.UserAlreadyExistsException;
import com.vit.minibank.domain.mapper.UserMapper;
import com.vit.minibank.dto.request.user.UserCreateReq;
import com.vit.minibank.dto.request.user.UserWithAccountsReq;
import com.vit.minibank.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    private static final String LOGIN = "testLogin";
    private static final String NAME = "TestName";

    @Test
    public void testFindAllUsers() {

        List<User> expectedUsers = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findAllUsers();

        assertEquals(expectedUsers, actualUsers);
        verify(userRepository).findAll();
    }

    @Test
    public void testFindUser() {
        // Заглушка для метода findByLogin
        when(userRepository.findByLogin(eq(LOGIN))).thenReturn(Optional.of(createTestUser()));

        // Вызываем метод и проверяем результат
        UserWithAccountsReq request = new UserWithAccountsReq();
        request.setLogin(LOGIN);
        User user = userService.findUser(request);

        assertNotNull(user);
        assertEquals(LOGIN, user.getLogin());

        // Проверяем, что метод findByLogin был вызван ровно один раз и точно с этим логином
        verify(userRepository, times(1)).findByLogin(eq(LOGIN));
    }

    @Test
    public void testCreateUser() {

        String login = "newUser";
        UserCreateReq request = new UserCreateReq();
        request.setLogin(login);

        User expectedUser = new User();

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());
        when(mapper.mapUserFromRequest(request)).thenReturn(expectedUser);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        User actualUser = userService.createUser(request);

        assertEquals(expectedUser, actualUser);
        verify(userRepository).findByLogin(login);
        verify(mapper).mapUserFromRequest(request);
        verify(userRepository).save(expectedUser);
    }

    @Test
    public void testCreateUserWithExistingLogin() {

        String login = "existingUser";
        UserCreateReq request = new UserCreateReq();
        request.setLogin(login);

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(request));
        verify(userRepository).findByLogin(login);
    }

    private User createTestUser() {
        User user = new User();
        user.setLogin(LOGIN);
        user.setName(NAME);
        user.setCreationDate(new Timestamp(System.currentTimeMillis()));
        user.setModificationDate(new Timestamp(System.currentTimeMillis()));
        return user;
    }
}