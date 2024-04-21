package mappers.user;

import dto.user.CreateUserDto;
import entities.User;
import entities.UserRole;
import mappers.Mapper;

public class CreateUserMapper implements Mapper<CreateUserDto, User> {
    @Override
    public User map(CreateUserDto createUserDto) {
        return new User(null,
                UserRole.USER,
                createUserDto.getFirstName(),
                createUserDto.getLastName(),
                createUserDto.getPassword(),
                createUserDto.getEmail()
                );
    }
}