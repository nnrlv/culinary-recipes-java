package entities;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {
    private Long idUser;
    private final UserRole role;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public User(UserRole role, String firstName, String lastName, String password, String email) {
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(idUser, user.idUser) &&
                role == user.role &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, role, firstName, lastName, email);
    }
}
