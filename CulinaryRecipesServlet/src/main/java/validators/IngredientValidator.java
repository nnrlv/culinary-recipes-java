package validators;

import dto.IngredientDto;

public class IngredientValidator {

    public void validate(IngredientDto ingredient) {
        if (ingredient.getName().isEmpty())
            throw new IllegalArgumentException("Name is required");
        if (ingredient.getName().length() < 3)
            throw new IllegalArgumentException("Name is too short");
    }
}