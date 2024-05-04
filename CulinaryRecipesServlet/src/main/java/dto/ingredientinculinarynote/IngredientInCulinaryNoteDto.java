package dto.ingredientinculinarynote;

import lombok.*;
import entities.Ingredient;
import entities.CulinaryNote;
import entities.UnitOfMeasurement;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class IngredientInCulinaryNoteDto {
    Ingredient ingredient;
    CulinaryNote culinaryNote;
    UnitOfMeasurement unitOfMeasurement;
    float amount;
}
