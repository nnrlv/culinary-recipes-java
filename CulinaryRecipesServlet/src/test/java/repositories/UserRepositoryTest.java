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

        userRepository.create(user);
        User userFromRepository = userRepository.getByEmail(user.getEmail());
        user.setIdUser(userFromRepository.getIdUser());

        assertEquals(user, userFromRepository);
        userRepository.delete(userFromRepository.getIdUser());
    }

    @Test
    void getAllUsersTest() throws EmailAlreadyTakenException {
        User user1 = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test1.test@example.com");
        User user2 = new User(null, UserRole.USER, "test2", "test2",
                "password2", "test2.test@example.com");

        userRepository.create(user1);
        userRepository.create(user2);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        user1.setPassword(passwordHasher.hashPassword(user1.getPassword()));
        user2.setPassword(passwordHasher.hashPassword(user2.getPassword()));

        List<User> usersFromRepository = userRepository.getAll();

        user1.setIdUser(usersFromRepository.get(0).getIdUser());
        user2.setIdUser(usersFromRepository.get(1).getIdUser());

        assertTrue(users.size() == usersFromRepository.size() &&
                usersFromRepository.containsAll(users) && users.containsAll(usersFromRepository));

        for (User user : users) {
            userRepository.delete(user.getIdUser());
        }
    }

    @Test
    void GetUserByIdTest() throws EmailAlreadyTakenException {
        User user = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com");
        userRepository.create(user);

        User found = userRepository.getByEmail(user.getEmail());
        user.setPassword(passwordHasher.hashPassword(user.getPassword()));
        user.setIdUser(found.getIdUser());

        assertEquals(user, found);
        userRepository.delete(found.getIdUser());
    }

    @Test
    void GetUserByEmailTest() throws EmailAlreadyTakenException {
        User user = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com");
        userRepository.create(user);

        User found = userRepository.getByEmail(user.getEmail());
        user.setPassword(passwordHasher.hashPassword(user.getPassword()));
        user.setIdUser(found.getIdUser());

        assertEquals(user, found);
        userRepository.delete(found.getIdUser());
    }

    @Test
    void updateUserTest() throws EmailAlreadyTakenException {
        User user = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test1.test@example.com");
        userRepository.create(user);

        User userToUpdate = userRepository.getByEmail(user.getEmail());
        userToUpdate.setPassword("new");

        userRepository.update(userToUpdate);
        User updatedUser = userRepository.getByEmail(userToUpdate.getEmail());
        userToUpdate.setPassword(passwordHasher.hashPassword(userToUpdate.getPassword()));
        userToUpdate.setIdUser(updatedUser.getIdUser());

        assertEquals(updatedUser, userToUpdate);
        userRepository.delete(userToUpdate.getIdUser());
    }

    @Test
    void deleteUserTest() throws EmailAlreadyTakenException {
        User user = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com");
        userRepository.create(user);

        User created = userRepository.getByEmail(user.getEmail());

        userRepository.delete(created.getIdUser());
        assertNull(userRepository.getById(created.getIdUser()));
    }
}
