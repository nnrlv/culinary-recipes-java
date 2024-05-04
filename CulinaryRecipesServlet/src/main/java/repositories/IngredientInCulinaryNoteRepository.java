package repositories;

import entities.CulinaryNote;
import entities.Ingredient;
import entities.IngredientInCulinaryNote;
import entities.UnitOfMeasurement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.ConnectionManager;

public class IngredientInCulinaryNoteRepository {

    public static final String CREATE_INGREDIENT_IN_CULINARY_NOTE = """
            INSERT INTO ingredients_in_culinary_notes(ingredient_id, culinary_note_id, unit_of_measurement, amount) 
            VALUES (?, ?, ?, ?);
            """;
    public static final String GET_ALL_INGREDIENTS_BY_CULINARY_NOTE_ID = """
            SELECT ingredient_id, culinary_note_id, unit_of_measurement, amount
            FROM ingredients_in_culinary_notes
            WHERE culinary_note_id = ?
            """;

    public static final String DELETE_INGREDIENT_IN_CULINARY_NOTE = """
            DELETE FROM ingredients_in_culinary_notes
            WHERE ingredient_id = ? AND culinary_note_id = ?
            """;

    public IngredientInCulinaryNote create(IngredientInCulinaryNote ingredientInCulinaryNote) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_INGREDIENT_IN_CULINARY_NOTE,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, ingredientInCulinaryNote.getIngredient().getIdIngredient());
            preparedStatement.setLong(2, ingredientInCulinaryNote.getCulinaryNote().getIdCulinaryNote());
            preparedStatement.setString(3, ingredientInCulinaryNote.getUnitOfMeasurement().name());
            preparedStatement.setDouble(4, ingredientInCulinaryNote.getAmount());
            preparedStatement.executeUpdate();
            return ingredientInCulinaryNote;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IngredientInCulinaryNote> getAllByCulinaryNoteId(Long culinaryNoteId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_INGREDIENTS_BY_CULINARY_NOTE_ID)) {
            preparedStatement.setLong(1, culinaryNoteId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<IngredientInCulinaryNote> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildIngredientInCulinaryNoteEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(Long ingredientId, Long culinaryNoteId) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_INGREDIENT_IN_CULINARY_NOTE)) {
            preparedStatement.setLong(1, ingredientId);
            preparedStatement.setLong(2, culinaryNoteId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private IngredientInCulinaryNote buildIngredientInCulinaryNoteEntity(ResultSet resultSet) throws SQLException {
        IngredientRepository ingredientDao = new IngredientRepository();
        Ingredient ingredient = ingredientDao.getById(resultSet.getLong("ingredient_id"));

        CulinaryNoteRepository culinaryNoteRepository = new CulinaryNoteRepository();
        CulinaryNote culinaryNote = culinaryNoteRepository.getById(resultSet.getLong("culinary_note_id"));

        UnitOfMeasurement unitOfMeasurement = UnitOfMeasurement.valueOf(resultSet.getString("unit_of_measurement"));

        return new IngredientInCulinaryNote(
                ingredient,
                culinaryNote,
                unitOfMeasurement,
                resultSet.getFloat("amount")
        );
    }
}
