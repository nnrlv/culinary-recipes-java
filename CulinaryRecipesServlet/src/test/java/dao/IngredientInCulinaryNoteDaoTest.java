package dao;

import entities.Category;
import entities.CulinaryNote;
import entities.Ingredient;
import entities.IngredientInCulinaryNote;
import entities.UnitOfMeasurement;
import entities.User;
import entities.UserRole;
import exceptions.EmailAlreadyTakenException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientInCulinaryNoteDaoTest {

    private final IngredientInCulinaryNoteDao ingredientInCulinaryNoteDao = new IngredientInCulinaryNoteDao();
    private final UserDao userDao = new UserDao();
    private final CulinaryNoteDao culinaryNoteDao = new CulinaryNoteDao();
    private final IngredientDao ingredientDao = new IngredientDao();

    @Test
    void createNewIngredientInCulinaryNoteTest() throws EmailAlreadyTakenException {
        User user = new User(null, UserRole.USER, "TestUser", "TestUser",
                "password", "test.user@example.com");
        userDao.create(user);

        CulinaryNote culinaryNote = new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "TestNote", "Description", "Instructions");
        culinaryNoteDao.create(culinaryNote);

        Ingredient ingredient = new Ingredient(null, "TestIngredient");
        ingredientDao.create(ingredient);

        IngredientInCulinaryNote ingredientInCulinaryNote =
                new IngredientInCulinaryNote(ingredient, culinaryNote, UnitOfMeasurement.GRAM, 100.0f);

        IngredientInCulinaryNote ingredientInCulinaryNoteFromDao =
                ingredientInCulinaryNoteDao.create(ingredientInCulinaryNote);

        assertEquals(ingredientInCulinaryNote, ingredientInCulinaryNoteFromDao);

        ingredientInCulinaryNoteDao.delete(ingredient.getIdIngredient(), culinaryNote.getIdCulinaryNote());
        ingredientDao.delete(ingredient.getIdIngredient());
        culinaryNoteDao.delete(culinaryNote.getIdCulinaryNote());
        userDao.delete(user.getIdUser());
    }

    @Test
    void getAllIngredientsInCulinaryNoteTest() throws EmailAlreadyTakenException {
        User user = new User(null, UserRole.USER, "TestUser", "TestUser",
                "password", "test.user@example.com");
        userDao.create(user);

        CulinaryNote culinaryNote = new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "TestNote", "Description", "Instructions");
        culinaryNoteDao.create(culinaryNote);

        Ingredient ingredient1 = new Ingredient(null, "Ingredient1");
        Ingredient ingredient2 = new Ingredient(null, "Ingredient2");

        ingredientDao.create(ingredient1);
        ingredientDao.create(ingredient2);

        IngredientInCulinaryNote ingredientInCulinaryNote1 =
                new IngredientInCulinaryNote(ingredient1, culinaryNote, UnitOfMeasurement.GRAM, 100.0f);
        IngredientInCulinaryNote ingredientInCulinaryNote2 =
                new IngredientInCulinaryNote(ingredient2, culinaryNote, UnitOfMeasurement.GRAM, 200.0f);

        List<IngredientInCulinaryNote> ingredientsInCulinaryNote = new ArrayList<>();
        ingredientsInCulinaryNote.add(ingredientInCulinaryNote1);
        ingredientsInCulinaryNote.add(ingredientInCulinaryNote2);

        ingredientInCulinaryNoteDao.create(ingredientInCulinaryNote1);
        ingredientInCulinaryNoteDao.create(ingredientInCulinaryNote2);

        List<IngredientInCulinaryNote> ingredientsInCulinaryNoteFromDao =
                ingredientInCulinaryNoteDao.getAllByCulinaryNoteId(culinaryNote.getIdCulinaryNote());

        assertTrue(ingredientsInCulinaryNote.size() == ingredientsInCulinaryNoteFromDao.size() &&
                ingredientsInCulinaryNoteFromDao.containsAll(ingredientsInCulinaryNote) && ingredientsInCulinaryNote.containsAll(ingredientsInCulinaryNoteFromDao));

        ingredientInCulinaryNoteDao.delete(ingredient1.getIdIngredient(), culinaryNote.getIdCulinaryNote());
        ingredientInCulinaryNoteDao.delete(ingredient2.getIdIngredient(), culinaryNote.getIdCulinaryNote());
        ingredientDao.delete(ingredient1.getIdIngredient());
        ingredientDao.delete(ingredient2.getIdIngredient());
        culinaryNoteDao.delete(culinaryNote.getIdCulinaryNote());
        userDao.delete(user.getIdUser());
    }

    @Test
    void deleteIngredientInCulinaryNoteTest() throws EmailAlreadyTakenException {
        User user = new User(null, UserRole.USER, "TestUser", "TestUser",
                "password", "test.user@example.com");
        userDao.create(user);

        CulinaryNote culinaryNote = new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "TestNote", "Description", "Instructions");
        culinaryNoteDao.create(culinaryNote);

        Ingredient ingredient = new Ingredient(null, "TestIngredient");
        ingredientDao.create(ingredient);

        IngredientInCulinaryNote ingredientInCulinaryNote =
                new IngredientInCulinaryNote(ingredient, culinaryNote, UnitOfMeasurement.GRAM, 100.0f);
        ingredientInCulinaryNoteDao.create(ingredientInCulinaryNote);

        ingredientInCulinaryNoteDao.delete(ingredient.getIdIngredient(), culinaryNote.getIdCulinaryNote());

        assertTrue(ingredientInCulinaryNoteDao.getAllByCulinaryNoteId(culinaryNote.getIdCulinaryNote()).isEmpty());

        culinaryNoteDao.delete(culinaryNote.getIdCulinaryNote());
        ingredientDao.delete(ingredient.getIdIngredient());
        userDao.delete(user.getIdUser());
    }
}
