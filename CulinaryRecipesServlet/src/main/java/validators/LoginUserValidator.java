package validators;


import java.util.regex.Pattern;

public class LoginUserValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public void validate(String email, String password) {
        if (email.isEmpty())
            throw new IllegalArgumentException("Email is required");
        if (!EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException("Wrong email");
        if (password.isEmpty())
            throw new IllegalArgumentException("Password is required");
        if (password.length() < 8)
            throw new IllegalArgumentException("Password is too short");
    }
}