package entities;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class User {
    private Long idUser;
    private UserRole role;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
