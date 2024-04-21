package dto.culinarynote;

import lombok.*;
import java.util.List;
import entities.User;
import entities.Category;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class CulinaryNoteDto {
    private Long idCulinaryNote;
    private List<Category> categories;
    private User user;
    private String name;
    private String description;
    private String instructions;
}