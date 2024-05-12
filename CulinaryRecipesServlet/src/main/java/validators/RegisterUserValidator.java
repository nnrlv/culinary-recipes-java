package validators;

import dto.UserDto;

import java.util.regex.Pattern;

public class RegisterUserValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public void validate(UserDto user) {
        if (user.getEmail().isEmpty())
            throw new IllegalArgumentException("Email is required");
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches())
            throw new IllegalArgumentException("Wrong email");
        if (user.getFirstName().isEmpty())
            throw new IllegalArgumentException("First name is required");
        if (user.getLastName().isEmpty())
            throw new IllegalArgumentException("Last name is required");
        if (user.getPassword().isEmpty())
            throw new IllegalArgumentException("Password is required");
        if (user.getPassword().length() < 8)
            throw new IllegalArgumentException("Password is too short");
    }
}