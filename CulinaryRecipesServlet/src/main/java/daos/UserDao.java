package daos;

import entities.User;
import entities.UserRole;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.ConnectionManager;

public class UserDao implements Dao<Long, User> {

    public static final String CREATE_USER = """
            INSERT INTO users(role, name, surname, password, email) 
            VALUES (?, ?, ?, ?, ?);
            """;

    public static final String GET_ALL_USERS = """
            SELECT user_id, role, name, surname, email
            FROM users
            """;

    public static final String GET_USER_BY_ID = """
            SELECT user_id, role, name, surname, email
            FROM users
            WHERE user_id = ?
            """;

    public static  final String UPDATE_USER = """
            UPDATE users 
            SET name = ?, 
                surname = ?, 
                role = ?, 
                password = ?, 
                email = ? 
            WHERE user_id = ?;
            """;

    public static final String DELETE_USER = """
            DELETE FROM users
            WHERE user_id = ?
            """;

    @Override
    public User create(User user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER)) {
            preparedStatement.setString(1, user.getRole().name());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildUserEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildUserEntity(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getRole().name());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private User buildUserEntity(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("user_id"),
                UserRole.valueOf(resultSet.getString("role")),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                "",
                resultSet.getString("email")
                );
    }
}