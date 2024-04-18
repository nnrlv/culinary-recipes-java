package entities;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Ingredient {
    Long idIngredient;
    String name;
}
