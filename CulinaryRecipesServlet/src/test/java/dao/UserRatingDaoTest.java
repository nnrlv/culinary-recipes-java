package dao;

import entities.Category;
import entities.CulinaryNote;
import entities.User;
import entities.UserRating;
import entities.UserRole;
import exceptions.EmailAlreadyTakenException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRatingDaoTest {

    private final UserRatingDao userRatingDao = new UserRatingDao();
    private final UserDao userDao = new UserDao();
    private final CulinaryNoteDao culinaryNoteDao = new CulinaryNoteDao();

    @Test
    void createNewUserRatingTest() throws EmailAlreadyTakenException {
        User user = new User(null, UserRole.USER, "TestUser", "TestUser",
                "password", "test.user@example.com");
        userDao.create(user);

        CulinaryNote culinaryNote = new CulinaryNote(null, new ArrayList<>(),
                user, "TestNote", "Description", "Instructions");
        culinaryNoteDao.create(culinaryNote);

        UserRating userRating = new UserRating(user, culinaryNote, (short) 5, "Great note!");

        UserRating userRatingFromDao = userRatingDao.create(userRating);

        assertEquals(userRating, userRatingFromDao);

        userRatingDao.delete(user.getIdUser(), culinaryNote.getIdCulinaryNote());
        culinaryNoteDao.delete(culinaryNote.getIdCulinaryNote());
        userDao.delete(user.getIdUser());
    }

    @Test
    void getAllUserRatingsByCulinaryNoteIdTest() throws EmailAlreadyTakenException {
        User user0 = new User(null, UserRole.USER, "TestUser0", "TestUser0",
                "password", "test0.user@example.com");
        User user1 = new User(null, UserRole.USER, "TestUser1", "TestUser1",
                "password", "test1.user@example.com");
        User user2 = new User(null, UserRole.USER, "TestUser2", "TestUser2",
                "password", "test2.user@example.com");
        userDao.create(user0);
        userDao.create(user1);
        userDao.create(user2);

        CulinaryNote culinaryNote = new CulinaryNote(null, Arrays.asList(Category.APPETIZER), user0,
                "TestNote", "Description", "Instructions");
        culinaryNoteDao.create(culinaryNote);

        UserRating userRating1 = new UserRating(user1, culinaryNote, (short) 5, "Great note!");
        UserRating userRating2 = new UserRating(user2, culinaryNote, (short) 4, "Nice recipe!");

        List<UserRating> userRatings = new ArrayList<>();
        userRatings.add(userRating1);
        userRatings.add(userRating2);

        userRatingDao.create(userRating1);
        userRatingDao.create(userRating2);

        List<UserRating> userRatingsFromDao = userRatingDao.getAllByCulinaryNoteId(culinaryNote.getIdCulinaryNote());

        assertTrue(userRatings.size() == userRatingsFromDao.size() &&
                userRatingsFromDao.containsAll(userRatings) && userRatings.containsAll(userRatingsFromDao));

        userRatingDao.delete(user1.getIdUser(), culinaryNote.getIdCulinaryNote());
        userRatingDao.delete(user2.getIdUser(), culinaryNote.getIdCulinaryNote());
        culinaryNoteDao.delete(culinaryNote.getIdCulinaryNote());
        userDao.delete(user0.getIdUser());
        userDao.delete(user1.getIdUser());
        userDao.delete(user2.getIdUser());
    }

    @Test
    void deleteUserRatingTest() throws EmailAlreadyTakenException {
        User user = new User(null, UserRole.USER, "TestUser0", "TestUser0",
                "password", "test0.user@example.com");
        userDao.create(user);

        CulinaryNote culinaryNote = new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "TestNote", "Description", "Instructions");
        culinaryNoteDao.create(culinaryNote);

        UserRating userRating = new UserRating(user, culinaryNote, (short) 5, "Great note!");
        userRatingDao.create(userRating);

        userRatingDao.delete(user.getIdUser(), culinaryNote.getIdCulinaryNote());

        assertTrue(userRatingDao.getAllByCulinaryNoteId(culinaryNote.getIdCulinaryNote()).isEmpty());

        culinaryNoteDao.delete(culinaryNote.getIdCulinaryNote());
        userDao.delete(user.getIdUser());
    }
}
