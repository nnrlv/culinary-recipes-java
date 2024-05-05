package services;

import entities.IngredientInCulinaryNote;
import repositories.IngredientInCulinaryNoteRepository;
import dto.IngredientInCulinaryNoteDto;
import lombok.AllArgsConstructor;
import mappers.IngredientInCulinaryNoteMapper;
import java.util.List;

@AllArgsConstructor
public class IngredientInCulinaryNoteService {
    private final IngredientInCulinaryNoteRepository ingredientInCulinaryNoteRepository;
    private final IngredientInCulinaryNoteMapper ingredientInCulinaryNoteMapper;

    public IngredientInCulinaryNote create(IngredientInCulinaryNoteDto createIngredientInCulinaryNoteDto) {
        return ingredientInCulinaryNoteRepository.create(ingredientInCulinaryNoteMapper
                .map(createIngredientInCulinaryNoteDto));
    }

    public List<IngredientInCulinaryNoteDto> getAllByCulinaryNoteId(Long idCulinaryNote) {
        return ingredientInCulinaryNoteRepository.getAllByCulinaryNoteId(idCulinaryNote).stream()
                .map(ingredientInCulinaryNoteMapper::map).toList();
    }
    public boolean delete(Long idIngredient, Long idCulinaryNote) {
        return ingredientInCulinaryNoteRepository.delete(idIngredient, idCulinaryNote);
    }
}
