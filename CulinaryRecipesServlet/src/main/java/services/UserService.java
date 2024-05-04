package services;

import repositories.UserRepository;
import dto.user.CreateUserDto;
import dto.user.UserDto;
import entities.User;
import exceptions.EmailAlreadyTakenException;
import lombok.AllArgsConstructor;
import mappers.user.CreateUserMapper;
import mappers.user.UserMapper;
import java.util.List;

@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CreateUserMapper createUserMapper;
    public User create(CreateUserDto userDto) throws EmailAlreadyTakenException {
        return userRepository.create(createUserMapper.map(userDto));
    }
    public List<UserDto> getAll() {
        return userRepository.getAll().stream().map(userMapper::map).toList();
    }
    public UserDto getByEmail(String email) {
        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email can't empty");
        }
        return userMapper.map(userRepository.getByEmail(email));
    }
    public void updateUser(UserDto userDto) {
        userRepository.update(userMapper.map(userDto));
    }
    public boolean delete(Long id) {
        return userRepository.delete(id);
    }
}