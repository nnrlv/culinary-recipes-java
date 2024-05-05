package dto;

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
}