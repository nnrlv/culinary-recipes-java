package dao;

import entities.User;
import entities.UserRole;
import exceptions.EmailAlreadyTakenException;
import org.junit.jupiter.api.*;
import utils.PasswordHasher;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private final UserDao userDao = new UserDao();

    private final PasswordHasher passwordHasher = new PasswordHasher();

    @Test
    void createNewUserTest() throws EmailAlreadyTakenException {
        User user = new User(null, UserRole.USER, "Test", "Test",
                "password", "test.test@example.com");

        User userFromDao = userDao.create(user);

        assertEquals(user, userFromDao);
        userDao.delete(userFromDao.getIdUser());
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

        userDao.create(user1);
        userDao.create(user2);

        user1.setPassword(passwordHasher.hashPassword(user1.getPassword()));
        user2.setPassword(passwordHasher.hashPassword(user2.getPassword()));

        List<User> usersFromDao = userDao.getAll();

        assertTrue(users.size() == usersFromDao.size() &&
                usersFromDao.containsAll(users) && users.containsAll(usersFromDao));

        for (User user : users) {
            userDao.delete(user.getIdUser());
        }
    }

    @Test
    void GetUserByIdTest() throws EmailAlreadyTakenException {
        User user = userDao.create(new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com"));
        User found = userDao.getById(user.getIdUser());
        user.setPassword(passwordHasher.hashPassword(user.getPassword()));
        assertEquals(user, found);
        userDao.delete(found.getIdUser());
    }

    @Test
    void GetUserByEmailTest() throws EmailAlreadyTakenException {
        User user = userDao.create(new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com"));
        User found = userDao.getByEmail(user.getEmail());
        user.setPassword(passwordHasher.hashPassword(user.getPassword()));
        assertEquals(user, found);
        userDao.delete(found.getIdUser());
    }

    @Test
    void updateUserTest() throws EmailAlreadyTakenException {
        User userToUpdate = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test1.test@example.com");
        userDao.create(userToUpdate);

        userToUpdate.setPassword("new");

        userDao.update(userToUpdate);
        User updatedUser = userDao.getById(userToUpdate.getIdUser());
        userToUpdate.setPassword(passwordHasher.hashPassword(userToUpdate.getPassword()));

        assertEquals(updatedUser, userToUpdate);
        userDao.delete(userToUpdate.getIdUser());
    }

    @Test
    void deleteUserTest() throws EmailAlreadyTakenException {
        User user = userDao.create(new User(null, UserRole.USER, "test1", "test1",
                "password1", "test1.test@example.com"));

        userDao.delete(user.getIdUser());
        assertNull(userDao.getById(user.getIdUser()));
    }
}
