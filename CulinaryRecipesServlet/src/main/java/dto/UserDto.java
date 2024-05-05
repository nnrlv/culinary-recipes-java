package dto;

import entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserDto {
    private Long idUser;
    private UserRole role;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
