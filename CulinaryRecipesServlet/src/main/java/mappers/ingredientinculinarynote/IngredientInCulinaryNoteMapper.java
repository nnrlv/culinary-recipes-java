package mappers.ingredientinculinarynote;

import entities.IngredientInCulinaryNote;
import dto.ingredientinculinarynote.IngredientInCulinaryNoteDto;
import mappers.Mapper;

public class IngredientInCulinaryNoteMapper implements Mapper<IngredientInCulinaryNote, IngredientInCulinaryNoteDto> {
    @Override
    public IngredientInCulinaryNoteDto map(IngredientInCulinaryNote ingredientInCulinaryNote) {
        return new IngredientInCulinaryNoteDto(
                ingredientInCulinaryNote.getIngredient(),
                ingredientInCulinaryNote.getCulinaryNote(),
                ingredientInCulinaryNote.getUnitOfMeasurement(),
                ingredientInCulinaryNote.getAmount());
    }
}