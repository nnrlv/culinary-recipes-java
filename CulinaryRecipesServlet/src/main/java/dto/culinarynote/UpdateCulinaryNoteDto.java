package dto.culinarynote;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class UpdateCulinaryNoteDto {
    private Long idCulinaryNote;
    private String name;
    private String description;
    private String instructions;
}