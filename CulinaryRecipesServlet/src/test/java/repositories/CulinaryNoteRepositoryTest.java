package repositories;

import entities.Category;
import entities.CulinaryNote;
import entities.User;
import entities.UserRole;
import exceptions.EmailAlreadyTakenException;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CulinaryNoteRepositoryTest {

    private final CulinaryNoteRepository culinaryNoteRepository = new CulinaryNoteRepository();
    private final UserRepository userRepository = new UserRepository();

    @Test
    void createNewCulinaryNoteTest() throws EmailAlreadyTakenException {
        User userToCreate = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com");
        userRepository.create(userToCreate);
        User user = userRepository.getByEmail(userToCreate.getEmail());

        CulinaryNote culinaryNote = new CulinaryNote(null, Arrays.asList(Category.APPETIZER, Category.DESSERT),
                user, "Test Culinary Note", "Description", "Instructions");

        CulinaryNote culinaryNoteFromRepository = culinaryNoteRepository.create(culinaryNote);

        assertEquals(culinaryNote, culinaryNoteFromRepository);
        culinaryNoteRepository.delete(culinaryNoteFromRepository.getIdCulinaryNote());
        userRepository.delete(user.getIdUser());
    }

    @Test
    void getAllCulinaryNotesTest() throws EmailAlreadyTakenException {
        User userToCreate = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test10.test@example.com");
        userRepository.create(userToCreate);
        User user = userRepository.getByEmail(userToCreate.getEmail());

        CulinaryNote culinaryNote1 = culinaryNoteRepository.create(new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "Culinary Note 1", "Description", "Instructions"));

        CulinaryNote culinaryNote2 = culinaryNoteRepository.create(new CulinaryNote(null, Arrays.asList(Category.DESSERT),
                user, "Culinary Note 2", "Description", "Instructions"));

        List<CulinaryNote> culinaryNotes = new ArrayList<>();
        culinaryNotes.add(culinaryNote1);
        culinaryNotes.add(culinaryNote2);

        List<CulinaryNote> culinaryNotesFromRepository = culinaryNoteRepository.getAll();

        assertTrue(culinaryNotes.size() == culinaryNotesFromRepository.size() &&
                culinaryNotesFromRepository.containsAll(culinaryNotes) && culinaryNotes.containsAll(culinaryNotesFromRepository));

        for (CulinaryNote culinaryNote : culinaryNotes) {
            culinaryNoteRepository.delete(culinaryNote.getIdCulinaryNote());
        }

        userRepository.delete(user.getIdUser());
    }

    @Test
    void getCulinaryNoteByIdTest() throws EmailAlreadyTakenException {
        User userToCreate = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com");
        userRepository.create(userToCreate);
        User user = userRepository.getByEmail(userToCreate.getEmail());

        CulinaryNote culinaryNote = culinaryNoteRepository.create(new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "Culinary Note 1", "Description", "Instructions"));

        CulinaryNote found = culinaryNoteRepository.getById(culinaryNote.getIdCulinaryNote());
        assertEquals(culinaryNote, found);
        culinaryNoteRepository.delete(found.getIdCulinaryNote());
        userRepository.delete(user.getIdUser());
    }

    @Test
    void updateCulinaryNoteTest() throws EmailAlreadyTakenException {
        User userToCreate = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com");
        userRepository.create(userToCreate);
        User user = userRepository.getByEmail(userToCreate.getEmail());

        CulinaryNote culinaryNoteToUpdate = new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "Culinary Note 1", "Description", "Instructions");
        culinaryNoteRepository.create(culinaryNoteToUpdate);

        culinaryNoteToUpdate.setName("Updated Name");

        culinaryNoteRepository.update(culinaryNoteToUpdate);
        CulinaryNote updatedCulinaryNote = culinaryNoteRepository.getById(culinaryNoteToUpdate.getIdCulinaryNote());

        assertEquals(updatedCulinaryNote, culinaryNoteToUpdate);
        culinaryNoteRepository.delete(culinaryNoteToUpdate.getIdCulinaryNote());
        userRepository.delete(user.getIdUser());
    }

    @Test
    void deleteCulinaryNoteTest() throws EmailAlreadyTakenException {
        User userToCreate = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com");
        userRepository.create(userToCreate);
        User user = userRepository.getByEmail(userToCreate.getEmail());

        CulinaryNote culinaryNote = culinaryNoteRepository.create(new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "Culinary Note 1", "Description", "Instructions"));

        culinaryNoteRepository.delete(culinaryNote.getIdCulinaryNote());
        assertNull(culinaryNoteRepository.getById(culinaryNote.getIdCulinaryNote()));
        userRepository.delete(user.getIdUser());
    }
}
