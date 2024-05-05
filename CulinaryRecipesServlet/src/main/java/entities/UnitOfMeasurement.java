package entities;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public enum UnitOfMeasurement {
    GRAM,
    KILOGRAM,
    MILLILITER,
    LITER,
    TEASPOON,
    TABLESPOON,
    CUP,
    PIECE,
    PINCH,
    DASH;

    public static List<String> getAllNames() {
        return Arrays.stream(UnitOfMeasurement.values())
                .map(Enum::name)
                .toList();
    }
}
