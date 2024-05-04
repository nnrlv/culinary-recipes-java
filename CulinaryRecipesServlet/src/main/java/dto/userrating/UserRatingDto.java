package dto.userrating;

import entities.CulinaryNote;
import entities.User;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class UserRatingDto {
    User user;
    CulinaryNote culinaryNote;
    Short grade;
    String comment;
}