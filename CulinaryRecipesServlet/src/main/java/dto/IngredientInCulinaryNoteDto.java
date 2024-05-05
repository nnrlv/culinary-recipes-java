package dto;

import lombok.*;
import entities.UnitOfMeasurement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class IngredientInCulinaryNoteDto {
    IngredientDto ingredient;
    CulinaryNoteDto culinaryNote;
    UnitOfMeasurement unitOfMeasurement;
    double amount;
}
