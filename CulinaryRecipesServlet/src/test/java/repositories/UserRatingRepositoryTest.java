package repositories;

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

public class UserRatingRepositoryTest {

    private final UserRatingRepository userRatingRepository = new UserRatingRepository();
    private final UserRepository userRepository = new UserRepository();
    private final CulinaryNoteRepository culinaryNoteRepository = new CulinaryNoteRepository();

    @Test
    void createNewUserRatingTest() throws EmailAlreadyTakenException {
        User user = new User(null, UserRole.USER, "TestUser", "TestUser",
                "password", "test.user@example.com");
        userRepository.create(user);

        CulinaryNote culinaryNote = new CulinaryNote(null, new ArrayList<>(),
                user, "TestNote", "Description", "Instructions");
        culinaryNoteRepository.create(culinaryNote);

        UserRating userRating = new UserRating(user, culinaryNote, (short) 5, "Great note!");

        UserRating userRatingFromRepository = userRatingRepository.create(userRating);

        assertEquals(userRating, userRatingFromRepository);

        userRatingRepository.delete(user.getIdUser(), culinaryNote.getIdCulinaryNote());
        culinaryNoteRepository.delete(culinaryNote.getIdCulinaryNote());
        userRepository.delete(user.getIdUser());
    }

    @Test
    void getAllUserRatingsByCulinaryNoteIdTest() throws EmailAlreadyTakenException {
        User user0 = new User(null, UserRole.USER, "TestUser0", "TestUser0",
                "password", "test0.user@example.com");
        User user1 = new User(null, UserRole.USER, "TestUser1", "TestUser1",
                "password", "test1.user@example.com");
        User user2 = new User(null, UserRole.USER, "TestUser2", "TestUser2",
                "password", "test2.user@example.com");
        userRepository.create(user0);
        userRepository.create(user1);
        userRepository.create(user2);

        CulinaryNote culinaryNote = new CulinaryNote(null, Arrays.asList(Category.APPETIZER), user0,
                "TestNote", "Description", "Instructions");
        culinaryNoteRepository.create(culinaryNote);

        UserRating userRating1 = new UserRating(user1, culinaryNote, (short) 5, "Great note!");
        UserRating userRating2 = new UserRating(user2, culinaryNote, (short) 4, "Nice recipe!");

        List<UserRating> userRatings = new ArrayList<>();
        userRatings.add(userRating1);
        userRatings.add(userRating2);

        userRatingRepository.create(userRating1);
        userRatingRepository.create(userRating2);

        List<UserRating> userRatingsFromRepository = userRatingRepository.getAllByCulinaryNoteId(culinaryNote.getIdCulinaryNote());

        assertTrue(userRatings.size() == userRatingsFromRepository.size() &&
                userRatingsFromRepository.containsAll(userRatings) && userRatings.containsAll(userRatingsFromRepository));

        userRatingRepository.delete(user1.getIdUser(), culinaryNote.getIdCulinaryNote());
        userRatingRepository.delete(user2.getIdUser(), culinaryNote.getIdCulinaryNote());
        culinaryNoteRepository.delete(culinaryNote.getIdCulinaryNote());
        userRepository.delete(user0.getIdUser());
        userRepository.delete(user1.getIdUser());
        userRepository.delete(user2.getIdUser());
    }

    @Test
    void deleteUserRatingTest() throws EmailAlreadyTakenException {
        User user = new User(null, UserRole.USER, "TestUser0", "TestUser0",
                "password", "test0.user@example.com");
        userRepository.create(user);

        CulinaryNote culinaryNote = new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "TestNote", "Description", "Instructions");
        culinaryNoteRepository.create(culinaryNote);

        UserRating userRating = new UserRating(user, culinaryNote, (short) 5, "Great note!");
        userRatingRepository.create(userRating);

        userRatingRepository.delete(user.getIdUser(), culinaryNote.getIdCulinaryNote());

        assertTrue(userRatingRepository.getAllByCulinaryNoteId(culinaryNote.getIdCulinaryNote()).isEmpty());

        culinaryNoteRepository.delete(culinaryNote.getIdCulinaryNote());
        userRepository.delete(user.getIdUser());
    }
}
