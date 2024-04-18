package entities;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserRating {
    User user;
    CulinaryNote culinaryNote;
    Short grade;
    String comment;
}
