package services;

import dao.IngredientDao;
import dto.ingredient.CreateIngredientDto;
import dto.ingredient.IngredientDto;
import entities.Ingredient;
import mappers.ingredient.CreateIngredientMapper;
import mappers.ingredient.IngredientMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {

    @Mock
    private IngredientDao ingredientDao;
    @Mock
    private IngredientMapper ingredientMapper;
    @Mock
    private CreateIngredientMapper createIngredientMapper;
    @InjectMocks
    private IngredientService ingredientService;

    @Test
    void createNewIngredientTest() {
        CreateIngredientDto createIngredientDto = getCreateIngredientDto();
        Ingredient ingredient = getIngredient();

        when(createIngredientMapper.map(createIngredientDto)).thenReturn(ingredient);
        when(ingredientDao.create(ingredient)).thenReturn(ingredient);

        Ingredient result = ingredientService.create(createIngredientDto);

        assertThat(result).isEqualTo(ingredient);
    }

    @Test
    void getAllIngredientsTest() {
        Ingredient ingredient = getIngredient();
        List<Ingredient> ingredients = List.of(ingredient);
        IngredientDto ingredientDto = getIngredientDto();

        when(ingredientDao.getAll()).thenReturn(ingredients);
        when(ingredientMapper.map(ingredient)).thenReturn(ingredientDto);

        assertThat(ingredientService.getAll()).hasSize(1);
        assertThat(ingredientService.getAll().get(0)).isEqualTo(ingredientDto);
    }

    @Test
    void getByIdTest_IngredientExists() {
        IngredientDto ingredientDto = getIngredientDto();
        Ingredient ingredient = getIngredient();
        Long id = ingredient.getIdIngredient();

        when(ingredientMapper.map(ingredient)).thenReturn(ingredientDto);
        when(ingredientDao.getById(id)).thenReturn(ingredient);

        assertThat(ingredientService.getById(id)).isEqualTo(ingredientDto);
    }

    @Test
    void getByIdTest_IngredientDoesntExist() {
        Long id = 999L;
        when(ingredientDao.getById(id)).thenReturn(null);
        assertThat(ingredientService.getById(id)).isNull();
    }

    @Test
    void updateIngredientTest() {
        IngredientDto ingredientDto = getIngredientDto();
        Ingredient ingredient = getIngredient();

        when(ingredientMapper.map(ingredientDto)).thenReturn(ingredient);
        when(ingredientDao.update(ingredient)).thenReturn(true);

        assertThat(ingredientService.update(ingredientDto)).isTrue();
    }

    @Test
    void deleteIngredientTest_IngredientExists() {
        Long id = 777L;
        when(ingredientDao.delete(id)).thenReturn(true);
        assertThat(ingredientService.delete(id)).isTrue();
    }

    @Test
    void deleteIngredientTest_IngredientDoesntExist() {
        Long id = 666L;
        when(ingredientDao.delete(id)).thenReturn(false);
        assertThat(ingredientService.delete(id)).isFalse();
    }

    private static IngredientDto getIngredientDto() {
        return new IngredientDto(1L, "Test Ingredient");
    }

    private static Ingredient getIngredient() {
        return new Ingredient(1L, "Test Ingredient");
    }

    private static CreateIngredientDto getCreateIngredientDto() {
        return new CreateIngredientDto("Test Ingredient");
    }
}
