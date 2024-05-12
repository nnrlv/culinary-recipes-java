package dto;

import entities.User;
import lombok.*;

import java.util.List;

import entities.Category;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class CulinaryNoteDto {
    Long idCulinaryNote;
    private List<Category> categories;
    private List<IngredientInCulinaryNoteDto> ingredientsInCulinaryNote;
    private UserDto user;
    private String name;
    private String description;
    private String instructions;

    public CulinaryNoteDto(List<Category> categories,
                           UserDto user, String name, String description, String instructions) {
        this.categories = categories;
        this.user = user;
        this.name = name;
        this.description = description;
        this.instructions = instructions;
    }
}