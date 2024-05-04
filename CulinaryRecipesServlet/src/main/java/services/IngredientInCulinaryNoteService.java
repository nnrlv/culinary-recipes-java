package services;

import entities.IngredientInCulinaryNote;
import repositories.IngredientInCulinaryNoteRepository;
import dto.ingredientinculinarynote.IngredientInCulinaryNoteDto;
import dto.ingredientinculinarynote.CreateIngredientInCulinaryNoteDto;
import lombok.AllArgsConstructor;
import mappers.ingredientinculinarynote.IngredientInCulinaryNoteMapper;
import mappers.ingredientinculinarynote.CreateIngredientInCulinaryNoteMapper;
import java.util.List;

@AllArgsConstructor
public class IngredientInCulinaryNoteService {
    private final IngredientInCulinaryNoteRepository ingredientInCulinaryNoteRepository;
    private final CreateIngredientInCulinaryNoteMapper createIngredientInCulinaryNoteMapper;
    private final IngredientInCulinaryNoteMapper ingredientInCulinaryNoteMapper;

    public IngredientInCulinaryNote create(CreateIngredientInCulinaryNoteDto createIngredientInCulinaryNoteDto) {
        return ingredientInCulinaryNoteRepository.create(createIngredientInCulinaryNoteMapper
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
