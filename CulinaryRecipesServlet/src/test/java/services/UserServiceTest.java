package services;

import repositories.UserRepository;
import dto.UserDto;
import entities.UserRole;
import entities.User;
import exceptions.EmailAlreadyTakenException;
import mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() throws EmailAlreadyTakenException {
        UserDto userDto = new UserDto(null, UserRole.USER, "name", "surname", "password", "email");
        User user = new User(null, UserRole.USER, "name", "surname", "password", "email");
        when(userMapper.map(userDto)).thenReturn(user);
        when(userRepository.create(user)).thenReturn(true);

        boolean result = userService.create(userDto);

        assertThat(result).isTrue();
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = List.of(
                new User(null, UserRole.USER, "name", "surname", "password", "email1"),
                new User(null, UserRole.USER, "name", "surname", "password", "email2")
        );

        when(userRepository.getAll()).thenReturn(users);
        when(userMapper.map(users.get(0))).thenReturn(
                new UserDto( null, UserRole.USER, "name", "surname", "password", "email1"));
        when(userMapper.map(users.get(1))).thenReturn(
                new UserDto(null, UserRole.USER, "name", "surname", "password", "email2"));

        List<UserDto> result = userService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getEmail()).isEqualTo("email1");
        assertThat(result.get(1).getEmail()).isEqualTo("email2");
    }

    @Test
    public void testGetByEmail() {
        User user = new User(null, UserRole.USER, "name", "surname",
                "password", "email");
        when(userRepository.getByEmail("email")).thenReturn(user);
        when(userMapper.map(user)).thenReturn(new UserDto(null, UserRole.USER, "name", "surname",
                "password", "email"));

        UserDto result = userService.getByEmail("email");

        assertThat(result.getEmail()).isEqualTo("email");
    }

    @Test
    public void testGetByEmailWithEmptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> userService.getByEmail(""));
    }

    @Test
    public void testUpdateUser() {
        UserDto userDto = new UserDto(null, UserRole.USER, "name", "surname",
                "password", "email");
        User user = new User(null, UserRole.USER, "name", "surname",
                "password", "email");
        when(userMapper.map(userDto)).thenReturn(user);

        userService.updateUser(userDto);

        verify(userRepository).update(user);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;
        when(userRepository.delete(userId)).thenReturn(true);

        boolean result = userService.delete(userId);

        assertThat(result).isTrue();
    }
}
