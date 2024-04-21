package dao;

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

public class CulinaryNoteDaoTest {

    private final CulinaryNoteDao culinaryNoteDao = new CulinaryNoteDao();
    private final UserDao userDao = new UserDao();

    @Test
    void createNewCulinaryNoteTest() throws EmailAlreadyTakenException {
        User user = userDao.create(new User(null, UserRole.USER, "Test", "User",
                "password", "test.user@example.com"));

        CulinaryNote culinaryNote = new CulinaryNote(null, Arrays.asList(Category.APPETIZER, Category.DESSERT),
                user, "Test Culinary Note", "Description", "Instructions");

        CulinaryNote culinaryNoteFromDao = culinaryNoteDao.create(culinaryNote);

        assertEquals(culinaryNote, culinaryNoteFromDao);
        culinaryNoteDao.delete(culinaryNoteFromDao.getIdCulinaryNote());
        userDao.delete(user.getIdUser());
    }

    @Test
    void getAllCulinaryNotesTest() throws EmailAlreadyTakenException {
        User user = userDao.create(new User(null, UserRole.USER, "Test", "User",
                "password", "test.user@example.com"));

        CulinaryNote culinaryNote1 = culinaryNoteDao.create(new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "Culinary Note 1", "Description", "Instructions"));

        CulinaryNote culinaryNote2 = culinaryNoteDao.create(new CulinaryNote(null, Arrays.asList(Category.DESSERT),
                user, "Culinary Note 2", "Description", "Instructions"));

        List<CulinaryNote> culinaryNotes = new ArrayList<>();
        culinaryNotes.add(culinaryNote1);
        culinaryNotes.add(culinaryNote2);

        List<CulinaryNote> culinaryNotesFromDao = culinaryNoteDao.getAll();

        assertTrue(culinaryNotes.size() == culinaryNotesFromDao.size() &&
                culinaryNotesFromDao.containsAll(culinaryNotes) && culinaryNotes.containsAll(culinaryNotesFromDao));

        for (CulinaryNote culinaryNote : culinaryNotes) {
            culinaryNoteDao.delete(culinaryNote.getIdCulinaryNote());
        }

        userDao.delete(user.getIdUser());
    }

    @Test
    void getCulinaryNoteByIdTest() throws EmailAlreadyTakenException {
        User user = userDao.create(new User(null, UserRole.USER, "Test", "User",
                "password", "test.user@example.com"));

        CulinaryNote culinaryNote = culinaryNoteDao.create(new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "Culinary Note 1", "Description", "Instructions"));

        CulinaryNote found = culinaryNoteDao.getById(culinaryNote.getIdCulinaryNote());
        assertEquals(culinaryNote, found);
        culinaryNoteDao.delete(found.getIdCulinaryNote());
        userDao.delete(user.getIdUser());
    }

    @Test
    void updateCulinaryNoteTest() throws EmailAlreadyTakenException {
        User user = userDao.create(new User(null, UserRole.USER, "Test", "User",
                "password", "test.user@example.com"));

        CulinaryNote culinaryNoteToUpdate = new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "Culinary Note 1", "Description", "Instructions");
        culinaryNoteDao.create(culinaryNoteToUpdate);

        culinaryNoteToUpdate.setName("Updated Name");

        culinaryNoteDao.update(culinaryNoteToUpdate);
        CulinaryNote updatedCulinaryNote = culinaryNoteDao.getById(culinaryNoteToUpdate.getIdCulinaryNote());

        assertEquals(updatedCulinaryNote, culinaryNoteToUpdate);
        culinaryNoteDao.delete(culinaryNoteToUpdate.getIdCulinaryNote());
        userDao.delete(user.getIdUser());
    }

    @Test
    void deleteCulinaryNoteTest() throws EmailAlreadyTakenException {
        User user = userDao.create(new User(null, UserRole.USER, "Test", "User",
                "password", "test.user@example.com"));

        CulinaryNote culinaryNote = culinaryNoteDao.create(new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "Culinary Note 1", "Description", "Instructions"));

        culinaryNoteDao.delete(culinaryNote.getIdCulinaryNote());
        assertNull(culinaryNoteDao.getById(culinaryNote.getIdCulinaryNote()));
        userDao.delete(user.getIdUser());
    }
}
