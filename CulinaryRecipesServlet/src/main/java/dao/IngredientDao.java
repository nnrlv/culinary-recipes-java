package dao;

import entities.Ingredient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utils.ConnectionManager;

public class IngredientDao implements Dao<Long, Ingredient> {

    public static final String CREATE_INGREDIENT = """
            INSERT INTO ingredients(name)
            VALUES (?);
            """;

    public static final String GET_ALL_INGREDIENTS = """
            SELECT ingredient_id, name
            FROM ingredients
            """;

    public static final String GET_INGREDIENT_BY_ID = """
            SELECT ingredient_id, name
            FROM ingredients
            WHERE ingredient_id = ?
            """;

    public static final String UPDATE_INGREDIENT = """
            UPDATE ingredients
            SET name = ? 
            WHERE ingredient_id = ?;
            """;

    public static final String DELETE_INGREDIENT = """
            DELETE FROM ingredients
            WHERE ingredient_id = ?
            """;

    @Override
    public Ingredient create(Ingredient ingredient) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_INGREDIENT,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, ingredient.getName());
            preparedStatement.executeUpdate();
            final ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                ingredient.setIdIngredient(resultSet.getLong(1));
            }
            return ingredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ingredient> getAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_INGREDIENTS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ingredient> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildIngredientEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ingredient getById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_INGREDIENT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return buildIngredientEntity(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Ingredient ingredient) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INGREDIENT)) {
            preparedStatement.setString(1, ingredient.getName());
            preparedStatement.setLong(2, ingredient.getIdIngredient());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_INGREDIENT)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Ingredient buildIngredientEntity(ResultSet resultSet) throws SQLException {
        return new Ingredient(
                resultSet.getLong("ingredient_id"),
                resultSet.getString("name")
        );
    }
}
