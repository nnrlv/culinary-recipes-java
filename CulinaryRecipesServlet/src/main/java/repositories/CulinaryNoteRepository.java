package repositories;

import entities.Category;
import entities.CulinaryNote;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import utils.ConnectionManager;

public class CulinaryNoteRepository implements Repository<Long, CulinaryNote> {

    public static final String CREATE_CULINARY_NOTE = """
            INSERT INTO culinary_notes(category, user_id, name, description, instructions) 
            VALUES (?, ?, ?, ?, ?);
            """;

    public static final String GET_ALL_CULINARY_NOTES = """
            SELECT culinary_note_id, category, user_id, name, description, instructions
            FROM culinary_notes
            """;

    public static final String GET_CULINARY_NOTE_BY_ID = """
            SELECT culinary_note_id, category, user_id, name, description, instructions
            FROM culinary_notes
            WHERE culinary_note_id = ?
            """;

    public static final String UPDATE_CULINARY_NOTE = """
            UPDATE culinary_notes 
            SET category = ?, 
                user_id = ?, 
                name = ?, 
                description = ?, 
                instructions = ?
            WHERE culinary_note_id = ?;
            """;

    public static final String DELETE_CULINARY_NOTE = """
            DELETE FROM culinary_notes
            WHERE culinary_note_id = ?
            """;

    @Override
    public CulinaryNote create(CulinaryNote culinaryNote) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CULINARY_NOTE,
                     Statement.RETURN_GENERATED_KEYS)) {
            String categoriesString = culinaryNote.getCategories().stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
            preparedStatement.setString(1, categoriesString);
            preparedStatement.setLong(2, culinaryNote.getUser().getIdUser());
            preparedStatement.setString(3, culinaryNote.getName());
            preparedStatement.setString(4, culinaryNote.getDescription());
            preparedStatement.setString(5, culinaryNote.getInstructions());
            preparedStatement.executeUpdate();
            final ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                culinaryNote.setIdCulinaryNote(resultSet.getLong(1));
            }
            return culinaryNote;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CulinaryNote> getAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CULINARY_NOTES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CulinaryNote> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildCulinaryNoteEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CulinaryNote getById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CULINARY_NOTE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildCulinaryNoteEntity(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(CulinaryNote culinaryNote) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CULINARY_NOTE)) {
            String categoriesString = culinaryNote.getCategories().stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
            preparedStatement.setString(1, categoriesString);
            preparedStatement.setLong(2, culinaryNote.getUser().getIdUser());
            preparedStatement.setString(3, culinaryNote.getName());
            preparedStatement.setString(4, culinaryNote.getDescription());
            preparedStatement.setString(5, culinaryNote.getInstructions());
            preparedStatement.setLong(6, culinaryNote.getIdCulinaryNote());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CULINARY_NOTE)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private CulinaryNote buildCulinaryNoteEntity(ResultSet resultSet) throws SQLException {
        UserRepository userDao = new UserRepository();
        User user = userDao.getById(resultSet.getLong("user_id"));
        List<String> categoriesList = Arrays.asList(resultSet.getString("category").split(","));
        List<Category> categories = new ArrayList<>();
        for (String category : categoriesList) {
            categories.add(Category.valueOf(category.trim()));
        }
        return new CulinaryNote(
                resultSet.getLong("culinary_note_id"),
                categories,
                user,
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("instructions")
        );
    }
}
