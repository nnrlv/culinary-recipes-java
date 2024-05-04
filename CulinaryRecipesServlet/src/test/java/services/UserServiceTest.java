package services;

import repositories.UserRepository;
import dto.user.CreateUserDto;
import dto.user.UserDto;
import entities.UserRole;
import entities.User;
import exceptions.EmailAlreadyTakenException;
import mappers.user.CreateUserMapper;
import mappers.user.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private CreateUserMapper createUserMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void CreateNewUserTest_EmailNotExists() throws EmailAlreadyTakenException {
        CreateUserDto createUserDto = getCreateUserDto();
        User user = getUser();

        when(createUserMapper.map(createUserDto)).thenReturn(user);
        when(userRepository.create(user)).thenReturn(user);

        User result = userService.create(createUserDto);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void CreateNewUserTest_EmailAlreadyExists() throws EmailAlreadyTakenException {
        CreateUserDto createUserDto = getCreateUserDto();
        User user = getUser();

        when(createUserMapper.map(createUserDto)).thenReturn(user);
        when(userRepository.create(user)).thenThrow(EmailAlreadyTakenException.class);

        EmailAlreadyTakenException exception = assertThrows(EmailAlreadyTakenException.class,
                () -> userService.create(createUserDto));
    }

    @Test
    void getByEmailTest_UserExists() {
        UserDto userDto = getUserDto();
        User user = getUser();
        String email = user.getEmail();
        when(userMapper.map(user)).thenReturn(userDto);
        when(userRepository.getByEmail(email)).thenReturn(user);

        assertThat(userService.getByEmail(email)).isEqualTo(userDto);
    }

    @Test
    void getByEmailTest_UserDoesntExist() {
        User user = getUser();
        String email = user.getEmail();
        when(userRepository.getByEmail(email)).thenReturn(null);
        assertThat(userService.getByEmail(email)).isEqualTo(null);
    }
    @Test
    void getAllUsersTest_UsersNotEmpty() {
        User user = getUser();
        List<User> users = List.of(user);
        UserDto userDto = getUserDto();

        when(userRepository.getAll())
                .thenReturn(users);
        when(userMapper.map(user))
                .thenReturn(userDto);

        assertThat(userService.getAll())
                .hasSize(1);
        userService.getAll().stream().map(UserDto::getIdUser)
                .forEach(id -> assertThat(id).isEqualTo(1));
    }

    @Test
    void getAllUsersTest_UsersEmpty() {
        when(userRepository.getAll())
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> userService.getAll());
    }

    @Test
    void deleteUserTest_UserExists() {
        Long id = 777L;
        when(userRepository.delete(id)).thenReturn(true);
        assertThat(userService.delete(id)).isEqualTo(true);
    }
    @Test
    void deleteUserTest_UserDoesntExist() {
        Long id = 666L;
        when(userRepository.delete(id)).thenReturn(false);
        assertThat(userService.delete(id)).isEqualTo(false);
    }

    private static UserDto getUserDto() {
        return new UserDto(1L,
                UserRole.USER,
                "test1",
                "test1",
                "password",
                "test@gmail.com");
    }

    private static User getUser() {
        return new User(1L,
                UserRole.USER,
                "test1",
                "test1",
                "password",
                "test@gmail.com");

    }

    private static CreateUserDto getCreateUserDto() {
        return new CreateUserDto("test1",
                "test1",
                "password",
                "test@gmail.com");
    }
}