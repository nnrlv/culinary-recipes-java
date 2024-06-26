package mappers;

import dto.IngredientDto;
import entities.Ingredient;

public class IngredientMapper implements Mapper<Ingredient, IngredientDto> {
    @Override
    public IngredientDto map(Ingredient ingredient) {
        return new IngredientDto(
                ingredient.getIdIngredient(),
                ingredient.getName());
    }

    public Ingredient map(IngredientDto ingredientDto) {
        return new Ingredient(
                ingredientDto.getIdIngredient(),
                ingredientDto.getName());
    }
}