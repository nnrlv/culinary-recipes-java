package mappers.user;

import dto.user.UserDto;
import entities.User;
import mappers.Mapper;

public class UserMapper implements Mapper<User, UserDto> {
    @Override
    public UserDto map(User user) {
        return new UserDto(
                user.getIdUser(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail());
    }

    public User map(UserDto userDto) {
        return new User(
                userDto.getIdUser(),
                userDto.getRole(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getPassword(),
                userDto.getEmail());
    }
}