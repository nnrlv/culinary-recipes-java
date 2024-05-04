package repositories;

import entities.Ingredient;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientRepositoryTest {

    private final IngredientRepository ingredientRepository = new IngredientRepository();

    @Test
    void createNewIngredientTest() {
        Ingredient ingredient = new Ingredient(null, "TestIngredient");

        Ingredient ingredientFromRepository = ingredientRepository.create(ingredient);

        assertEquals(ingredient, ingredientFromRepository);
        ingredientRepository.delete(ingredientFromRepository.getIdIngredient());
    }

    @Test
    void getAllIngredientsTest() {
        Ingredient ingredient1 = new Ingredient(null, "Ingredient1");
        Ingredient ingredient2 = new Ingredient(null, "Ingredient2");

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        ingredientRepository.create(ingredient1);
        ingredientRepository.create(ingredient2);

        List<Ingredient> ingredientsFromRepository = ingredientRepository.getAll();

        assertTrue(ingredients.size() == ingredientsFromRepository.size() &&
                ingredientsFromRepository.containsAll(ingredients) && ingredients.containsAll(ingredientsFromRepository));

        for (Ingredient ingredient : ingredients) {
            ingredientRepository.delete(ingredient.getIdIngredient());
        }
    }

    @Test
    void getIngredientByIdTest() {
        Ingredient ingredient = ingredientRepository.create(new Ingredient(null, "TestIngredient"));
        Ingredient found = ingredientRepository.getById(ingredient.getIdIngredient());
        assertEquals(ingredient, found);
        ingredientRepository.delete(found.getIdIngredient());
    }

    @Test
    void updateIngredientTest() {
        Ingredient ingredientToUpdate = new Ingredient(null, "IngredientToUpdate");
        ingredientRepository.create(ingredientToUpdate);

        ingredientToUpdate.setName("UpdatedName");

        ingredientRepository.update(ingredientToUpdate);
        Ingredient updatedIngredient = ingredientRepository.getById(ingredientToUpdate.getIdIngredient());

        assertEquals(updatedIngredient, ingredientToUpdate);
        ingredientRepository.delete(ingredientToUpdate.getIdIngredient());
    }

    @Test
    void deleteIngredientTest() {
        Ingredient ingredient = ingredientRepository.create(new Ingredient(null, "IngredientToDelete"));

        ingredientRepository.delete(ingredient.getIdIngredient());
        assertNull(ingredientRepository.getById(ingredient.getIdIngredient()));
    }
}
