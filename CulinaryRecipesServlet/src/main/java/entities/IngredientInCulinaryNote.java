package entities;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class IngredientInCulinaryNote {
    Ingredient ingredient;
    CulinaryNote culinaryNote;
    UnitOfMeasurement unitOfMeasurement;
    float amount;
}
