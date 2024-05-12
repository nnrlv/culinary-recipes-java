package entities;

import lombok.*;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class CulinaryNote {
    private Long idCulinaryNote;
    private List<Category> categories;
    private User user;
    private String name;
    private String description;
    private String instructions;

    public CulinaryNote(List<Category> categories, User user, String name, String description, String instructions) {
        this.categories = categories;
        this.user = user;
        this.name = name;
        this.description = description;
        this.instructions = instructions;
    }
}
