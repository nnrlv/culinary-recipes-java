package services;

import dao.IngredientDao;
import dto.ingredient.CreateIngredientDto;
import dto.ingredient.IngredientDto;
import entities.Ingredient;
import lombok.AllArgsConstructor;
import mappers.ingredient.CreateIngredientMapper;
import mappers.ingredient.IngredientMapper;

import java.util.List;

@AllArgsConstructor
public class IngredientService {
    private final IngredientDao ingredientDao;
    private final IngredientMapper ingredientMapper;
    private final CreateIngredientMapper createIngredientMapper;

    public Ingredient create(CreateIngredientDto ingredientDto) {
        return ingredientDao.create(createIngredientMapper.map(ingredientDto));
    }

    public List<IngredientDto> getAll() {
        return ingredientDao.getAll().stream().map(ingredientMapper::map).toList();
    }

    public IngredientDto getById(Long id) {
        return ingredientMapper.map(ingredientDao.getById(id));
    }

    public boolean update(IngredientDto ingredientDto) {
        return ingredientDao.update(ingredientMapper.map(ingredientDto));
    }

    public boolean delete(Long id) {
        return ingredientDao.delete(id);
    }
}
