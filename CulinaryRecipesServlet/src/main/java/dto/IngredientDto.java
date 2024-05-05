package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class IngredientDto {
    Long idIngredient;
    String name;
}
