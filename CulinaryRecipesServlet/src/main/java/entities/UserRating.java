package entities;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class UserRating {
    User user;
    CulinaryNote culinaryNote;
    Short grade;
    String comment;
}
