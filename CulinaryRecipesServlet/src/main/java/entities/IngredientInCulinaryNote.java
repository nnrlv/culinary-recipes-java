package entities;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class IngredientInCulinaryNote {
    Ingredient ingredient;
    CulinaryNote culinaryNote;
    UnitOfMeasurement unitOfMeasurement;
    double amount;
}
