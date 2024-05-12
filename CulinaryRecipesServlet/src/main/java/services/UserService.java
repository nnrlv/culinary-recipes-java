package services;

import repositories.UserRepository;
import dto.UserDto;
import exceptions.EmailAlreadyTakenException;
import lombok.AllArgsConstructor;
import mappers.UserMapper;
import java.util.List;

@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public boolean create(UserDto userDto) throws EmailAlreadyTakenException {
        return userRepository.create(userMapper.map(userDto));
    }
    public List<UserDto> getAll() {
        return userRepository.getAll().stream().map(userMapper::map).toList();
    }
    public UserDto getByEmail(String email) {
        try {
            return userMapper.map(userRepository.getByEmail(email));
        } catch (Exception e) {
            throw new RuntimeException("Wrong email or password");
        }
    }
    public void updateUser(UserDto userDto) {
        userRepository.update(userMapper.map(userDto));
    }
    public boolean delete(Long id) {
        return userRepository.delete(id);
    }
}