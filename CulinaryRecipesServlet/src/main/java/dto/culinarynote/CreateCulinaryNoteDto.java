package dto.culinarynote;

import lombok.*;
import entities.User;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class CreateCulinaryNoteDto {
    private User user;
    private String name;
    private String description;
    private String instructions;
}