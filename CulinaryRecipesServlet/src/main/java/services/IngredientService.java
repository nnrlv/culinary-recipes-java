package services;

import repositories.IngredientRepository;
import dto.ingredient.CreateIngredientDto;
import dto.ingredient.IngredientDto;
import entities.Ingredient;
import lombok.AllArgsConstructor;
import mappers.ingredient.CreateIngredientMapper;
import mappers.ingredient.IngredientMapper;

import java.util.List;

@AllArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final CreateIngredientMapper createIngredientMapper;

    public Ingredient create(CreateIngredientDto ingredientDto) {
        return ingredientRepository.create(createIngredientMapper.map(ingredientDto));
    }

    public List<IngredientDto> getAll() {
        return ingredientRepository.getAll().stream().map(ingredientMapper::map).toList();
    }

    public IngredientDto getById(Long id) {
        return ingredientMapper.map(ingredientRepository.getById(id));
    }

    public boolean update(IngredientDto ingredientDto) {
        return ingredientRepository.update(ingredientMapper.map(ingredientDto));
    }

    public boolean delete(Long id) {
        return ingredientRepository.delete(id);
    }
}
