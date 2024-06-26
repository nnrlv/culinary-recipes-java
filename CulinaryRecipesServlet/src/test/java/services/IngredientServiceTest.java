package services;

import repositories.IngredientRepository;
import dto.IngredientDto;
import entities.Ingredient;
import mappers.IngredientMapper;
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
    private IngredientRepository ingredientRepository;
    @Mock
    private IngredientMapper ingredientMapper;
    @InjectMocks
    private IngredientService ingredientService;

    @Test
    void createNewIngredientTest() {
        IngredientDto createIngredientDto = getIngredientDto();
        Ingredient ingredient = getIngredient();

        when(ingredientMapper.map(createIngredientDto)).thenReturn(ingredient);
        when(ingredientRepository.create(ingredient)).thenReturn(ingredient);

        Ingredient result = ingredientService.create(createIngredientDto);

        assertThat(result).isEqualTo(ingredient);
    }

    @Test
    void getAllIngredientsTest() {
        Ingredient ingredient = getIngredient();
        List<Ingredient> ingredients = List.of(ingredient);
        IngredientDto ingredientDto = getIngredientDto();

        when(ingredientRepository.getAll()).thenReturn(ingredients);
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
        when(ingredientRepository.getById(id)).thenReturn(ingredient);

        assertThat(ingredientService.getById(id)).isEqualTo(ingredientDto);
    }

    @Test
    void getByIdTest_IngredientDoesntExist() {
        Long id = 999L;
        when(ingredientRepository.getById(id)).thenReturn(null);
        assertThat(ingredientService.getById(id)).isNull();
    }

    @Test
    void updateIngredientTest() {
        IngredientDto ingredientDto = getIngredientDto();
        Ingredient ingredient = getIngredient();

        when(ingredientMapper.map(ingredientDto)).thenReturn(ingredient);
        when(ingredientRepository.update(ingredient)).thenReturn(true);

        assertThat(ingredientService.update(ingredientDto)).isTrue();
    }

    @Test
    void deleteIngredientTest_IngredientExists() {
        Long id = 777L;
        when(ingredientRepository.delete(id)).thenReturn(true);
        assertThat(ingredientService.delete(id)).isTrue();
    }

    @Test
    void deleteIngredientTest_IngredientDoesntExist() {
        Long id = 666L;
        when(ingredientRepository.delete(id)).thenReturn(false);
        assertThat(ingredientService.delete(id)).isFalse();
    }

    private static IngredientDto getIngredientDto() {
        return new IngredientDto(null, "Test Ingredient");
    }

    private static Ingredient getIngredient() {
        return new Ingredient(1L, "Test Ingredient");
    }
}
