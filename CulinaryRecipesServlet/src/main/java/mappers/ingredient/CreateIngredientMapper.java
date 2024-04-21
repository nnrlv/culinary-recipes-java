package mappers.ingredient;

import dto.ingredient.CreateIngredientDto;
import entities.Ingredient;
import mappers.Mapper;

public class CreateIngredientMapper implements Mapper<CreateIngredientDto, Ingredient> {
    @Override
    public Ingredient map(CreateIngredientDto createIngredientDto) {
        return new Ingredient(
                null,
                createIngredientDto.getName());
    }
}