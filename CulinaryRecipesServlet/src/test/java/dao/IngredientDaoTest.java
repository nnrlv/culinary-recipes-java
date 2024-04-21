package dao;

import entities.Ingredient;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientDaoTest {

    private final IngredientDao ingredientDao = new IngredientDao();

    @Test
    void createNewIngredientTest() {
        Ingredient ingredient = new Ingredient(null, "TestIngredient");

        Ingredient ingredientFromDao = ingredientDao.create(ingredient);

        assertEquals(ingredient, ingredientFromDao);
        ingredientDao.delete(ingredientFromDao.getIdIngredient());
    }

    @Test
    void getAllIngredientsTest() {
        Ingredient ingredient1 = new Ingredient(null, "Ingredient1");
        Ingredient ingredient2 = new Ingredient(null, "Ingredient2");

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        ingredientDao.create(ingredient1);
        ingredientDao.create(ingredient2);

        List<Ingredient> ingredientsFromDao = ingredientDao.getAll();

        assertTrue(ingredients.size() == ingredientsFromDao.size() &&
                ingredientsFromDao.containsAll(ingredients) && ingredients.containsAll(ingredientsFromDao));

        for (Ingredient ingredient : ingredients) {
            ingredientDao.delete(ingredient.getIdIngredient());
        }
    }

    @Test
    void getIngredientByIdTest() {
        Ingredient ingredient = ingredientDao.create(new Ingredient(null, "TestIngredient"));
        Ingredient found = ingredientDao.getById(ingredient.getIdIngredient());
        assertEquals(ingredient, found);
        ingredientDao.delete(found.getIdIngredient());
    }

    @Test
    void updateIngredientTest() {
        Ingredient ingredientToUpdate = new Ingredient(null, "IngredientToUpdate");
        ingredientDao.create(ingredientToUpdate);

        ingredientToUpdate.setName("UpdatedName");

        ingredientDao.update(ingredientToUpdate);
        Ingredient updatedIngredient = ingredientDao.getById(ingredientToUpdate.getIdIngredient());

        assertEquals(updatedIngredient, ingredientToUpdate);
        ingredientDao.delete(ingredientToUpdate.getIdIngredient());
    }

    @Test
    void deleteIngredientTest() {
        Ingredient ingredient = ingredientDao.create(new Ingredient(null, "IngredientToDelete"));

        ingredientDao.delete(ingredient.getIdIngredient());
        assertNull(ingredientDao.getById(ingredient.getIdIngredient()));
    }
}
