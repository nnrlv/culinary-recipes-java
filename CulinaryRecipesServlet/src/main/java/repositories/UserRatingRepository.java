package repositories;

import entities.CulinaryNote;
import entities.User;
import entities.UserRating;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.ConnectionManager;

public class UserRatingRepository {

    public static final String CREATE_USER_RATING = """
            INSERT INTO user_ratings(user_id, culinary_note_id, grade, comment) 
            VALUES (?, ?, ?, ?);
            """;

    public static final String GET_ALL_USER_RATINGS_BY_CULINARY_NOTE_ID = """
            SELECT user_id, culinary_note_id, grade, comment
            FROM user_ratings
            WHERE culinary_note_id = ?
            """;

    public static final String DELETE_USER_RATING = """
        DELETE FROM user_ratings
        WHERE user_id = ? AND culinary_note_id = ?
        """;

    public UserRating create(UserRating userRating) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER_RATING,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, userRating.getUser().getIdUser());
            preparedStatement.setLong(2, userRating.getCulinaryNote().getIdCulinaryNote());
            preparedStatement.setShort(3, userRating.getGrade());
            preparedStatement.setString(4, userRating.getComment());
            preparedStatement.executeUpdate();
            return userRating;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserRating> getAllByCulinaryNoteId(Long culinaryNoteId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USER_RATINGS_BY_CULINARY_NOTE_ID)) {
            preparedStatement.setLong(1, culinaryNoteId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserRating> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildUserRatingEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(Long userId, Long culinaryNoteId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_RATING)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, culinaryNoteId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private UserRating buildUserRatingEntity(ResultSet resultSet) throws SQLException {
        UserRepository userDao = new UserRepository();
        User user = userDao.getById(resultSet.getLong("user_id"));

        CulinaryNoteRepository culinaryNoteRepository = new CulinaryNoteRepository();
        CulinaryNote culinaryNote = culinaryNoteRepository.getById(resultSet.getLong("culinary_note_id"));

        return new UserRating(
                user,
                culinaryNote,
                resultSet.getShort("grade"),
                resultSet.getString("comment")
        );
    }
}
