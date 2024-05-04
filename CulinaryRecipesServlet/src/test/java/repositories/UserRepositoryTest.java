package repositories;

import entities.User;
import entities.UserRole;
import exceptions.EmailAlreadyTakenException;
import org.junit.jupiter.api.*;
import utils.PasswordHasher;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private final UserRepository userRepository = new UserRepository();

    private final PasswordHasher passwordHasher = new PasswordHasher();

    @Test
    void createNewUserTest() throws EmailAlreadyTakenException {
        User user = new User(null, UserRole.USER, "Test", "Test",
                "password", "test.test@example.com");

        User userFromRepository = userRepository.create(user);

        assertEquals(user, userFromRepository);
        userRepository.delete(userFromRepository.getIdUser());
    }

    @Test
    void getAllUsersTest() throws EmailAlreadyTakenException {
        User user1 = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test1.test@example.com");
        User user2 = new User(null, UserRole.USER, "test2", "test2",
                "password2", "test2.test@example.com");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        userRepository.create(user1);
        userRepository.create(user2);

        user1.setPassword(passwordHasher.hashPassword(user1.getPassword()));
        user2.setPassword(passwordHasher.hashPassword(user2.getPassword()));

        List<User> usersFromRepository = userRepository.getAll();

        assertTrue(users.size() == usersFromRepository.size() &&
                usersFromRepository.containsAll(users) && users.containsAll(usersFromRepository));

        for (User user : users) {
            userRepository.delete(user.getIdUser());
        }
    }

    @Test
    void GetUserByIdTest() throws EmailAlreadyTakenException {
        User user = userRepository.create(new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com"));
        User found = userRepository.getById(user.getIdUser());
        user.setPassword(passwordHasher.hashPassword(user.getPassword()));
        assertEquals(user, found);
        userRepository.delete(found.getIdUser());
    }

    @Test
    void GetUserByEmailTest() throws EmailAlreadyTakenException {
        User user = userRepository.create(new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com"));
        User found = userRepository.getByEmail(user.getEmail());
        user.setPassword(passwordHasher.hashPassword(user.getPassword()));
        assertEquals(user, found);
        userRepository.delete(found.getIdUser());
    }

    @Test
    void updateUserTest() throws EmailAlreadyTakenException {
        User userToUpdate = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test1.test@example.com");
        userRepository.create(userToUpdate);

        userToUpdate.setPassword("new");

        userRepository.update(userToUpdate);
        User updatedUser = userRepository.getById(userToUpdate.getIdUser());
        userToUpdate.setPassword(passwordHasher.hashPassword(userToUpdate.getPassword()));

        assertEquals(updatedUser, userToUpdate);
        userRepository.delete(userToUpdate.getIdUser());
    }

    @Test
    void deleteUserTest() throws EmailAlreadyTakenException {
        User user = userRepository.create(new User(null, UserRole.USER, "test1", "test1",
                "password1", "test1.test@example.com"));

        userRepository.delete(user.getIdUser());
        assertNull(userRepository.getById(user.getIdUser()));
    }
}
