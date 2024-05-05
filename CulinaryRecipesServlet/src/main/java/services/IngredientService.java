package services;

import repositories.IngredientRepository;
import dto.IngredientDto;
import entities.Ingredient;
import lombok.AllArgsConstructor;
import mappers.IngredientMapper;

import java.util.List;

@AllArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    public Ingredient create(IngredientDto ingredientDto) {
        return ingredientRepository.create(ingredientMapper.map(ingredientDto));
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
