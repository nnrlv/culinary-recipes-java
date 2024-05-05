package repositories;

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

public class IngredientInCulinaryNoteRepositoryTest {

    private final IngredientInCulinaryNoteRepository ingredientInCulinaryNoteRepository = new IngredientInCulinaryNoteRepository();
    private final UserRepository userRepository = new UserRepository();
    private final CulinaryNoteRepository culinaryNoteRepository = new CulinaryNoteRepository();
    private final IngredientRepository ingredientRepository = new IngredientRepository();

    @Test
    void createNewIngredientInCulinaryNoteTest() throws EmailAlreadyTakenException {
        User userToCreate = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com");
        userRepository.create(userToCreate);
        User user = userRepository.getByEmail(userToCreate.getEmail());

        CulinaryNote culinaryNote = new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "TestNote", "Description", "Instructions");
        culinaryNoteRepository.create(culinaryNote);

        Ingredient ingredient = new Ingredient(null, "TestIngredient");
        ingredientRepository.create(ingredient);

        IngredientInCulinaryNote ingredientInCulinaryNote =
                new IngredientInCulinaryNote(ingredient, culinaryNote, UnitOfMeasurement.GRAM, 100.0f);

        IngredientInCulinaryNote ingredientInCulinaryNoteFromRepository =
                ingredientInCulinaryNoteRepository.create(ingredientInCulinaryNote);

        assertEquals(ingredientInCulinaryNote, ingredientInCulinaryNoteFromRepository);

        ingredientInCulinaryNoteRepository.delete(ingredient.getIdIngredient(), culinaryNote.getIdCulinaryNote());
        ingredientRepository.delete(ingredient.getIdIngredient());
        culinaryNoteRepository.delete(culinaryNote.getIdCulinaryNote());
        userRepository.delete(user.getIdUser());
    }

    @Test
    void getAllIngredientsInCulinaryNoteTest() throws EmailAlreadyTakenException {
        User userToCreate = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com");
        userRepository.create(userToCreate);
        User user = userRepository.getByEmail(userToCreate.getEmail());

        CulinaryNote culinaryNote = new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "TestNote", "Description", "Instructions");
        culinaryNoteRepository.create(culinaryNote);

        Ingredient ingredient1 = new Ingredient(null, "Ingredient1");
        Ingredient ingredient2 = new Ingredient(null, "Ingredient2");

        ingredientRepository.create(ingredient1);
        ingredientRepository.create(ingredient2);

        IngredientInCulinaryNote ingredientInCulinaryNote1 =
                new IngredientInCulinaryNote(ingredient1, culinaryNote, UnitOfMeasurement.GRAM, 100.0f);
        IngredientInCulinaryNote ingredientInCulinaryNote2 =
                new IngredientInCulinaryNote(ingredient2, culinaryNote, UnitOfMeasurement.GRAM, 200.0f);

        List<IngredientInCulinaryNote> ingredientsInCulinaryNote = new ArrayList<>();
        ingredientsInCulinaryNote.add(ingredientInCulinaryNote1);
        ingredientsInCulinaryNote.add(ingredientInCulinaryNote2);

        ingredientInCulinaryNoteRepository.create(ingredientInCulinaryNote1);
        ingredientInCulinaryNoteRepository.create(ingredientInCulinaryNote2);

        List<IngredientInCulinaryNote> ingredientsInCulinaryNoteFromRepository =
                ingredientInCulinaryNoteRepository.getAllByCulinaryNoteId(culinaryNote.getIdCulinaryNote());

        assertTrue(ingredientsInCulinaryNote.size() == ingredientsInCulinaryNoteFromRepository.size() &&
                ingredientsInCulinaryNoteFromRepository.containsAll(ingredientsInCulinaryNote) && ingredientsInCulinaryNote.containsAll(ingredientsInCulinaryNoteFromRepository));

        ingredientInCulinaryNoteRepository.delete(ingredient1.getIdIngredient(), culinaryNote.getIdCulinaryNote());
        ingredientInCulinaryNoteRepository.delete(ingredient2.getIdIngredient(), culinaryNote.getIdCulinaryNote());
        ingredientRepository.delete(ingredient1.getIdIngredient());
        ingredientRepository.delete(ingredient2.getIdIngredient());
        culinaryNoteRepository.delete(culinaryNote.getIdCulinaryNote());
        userRepository.delete(user.getIdUser());
    }

    @Test
    void deleteIngredientInCulinaryNoteTest() throws EmailAlreadyTakenException {
        User userToCreate = new User(null, UserRole.USER, "test1", "test1",
                "password1", "test2.test@example.com");
        userRepository.create(userToCreate);
        User user = userRepository.getByEmail(userToCreate.getEmail());

        CulinaryNote culinaryNote = new CulinaryNote(null, Arrays.asList(Category.APPETIZER),
                user, "TestNote", "Description", "Instructions");
        culinaryNoteRepository.create(culinaryNote);

        Ingredient ingredient = new Ingredient(null, "TestIngredient");
        ingredientRepository.create(ingredient);

        IngredientInCulinaryNote ingredientInCulinaryNote =
                new IngredientInCulinaryNote(ingredient, culinaryNote, UnitOfMeasurement.GRAM, 100.0f);
        ingredientInCulinaryNoteRepository.create(ingredientInCulinaryNote);

        ingredientInCulinaryNoteRepository.delete(ingredient.getIdIngredient(), culinaryNote.getIdCulinaryNote());

        assertTrue(ingredientInCulinaryNoteRepository.getAllByCulinaryNoteId(culinaryNote.getIdCulinaryNote()).isEmpty());

        culinaryNoteRepository.delete(culinaryNote.getIdCulinaryNote());
        ingredientRepository.delete(ingredient.getIdIngredient());
        userRepository.delete(user.getIdUser());
    }
}
