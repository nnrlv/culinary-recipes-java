package entities;

import java.util.Arrays;
import java.util.List;

public enum Category {
    APPETIZER,
    MAIN_COURSE,
    DESSERT,
    DRINK,
    SALAD,
    SOUP,
    BREAD,
    BREAKFAST,
    SIDE_DISH,
    SAUCE,
    CONDIMENT;

    public static List<String> getAllNames() {
        return Arrays.stream(Category.values())
                .map(Enum::name)
                .toList();
    }
}
