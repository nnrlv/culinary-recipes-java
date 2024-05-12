package dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class IngredientDto {
    Long idIngredient;
    String name;

    public IngredientDto(String name) {
        this.name = name;
    }
}
