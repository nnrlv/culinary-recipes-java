package dto.user;

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
public class CreateUserDto {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}