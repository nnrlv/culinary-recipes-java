package entities;

import lombok.*;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class CulinaryNote {
    private Long idCulinaryNote;
    private List<Category> categories;
    private User user;
    private String name;
    private String description;
    private String instructions;
}
