package mappers;

import entities.IngredientInCulinaryNote;
import dto.IngredientInCulinaryNoteDto;

public class IngredientInCulinaryNoteMapper implements Mapper<IngredientInCulinaryNote, IngredientInCulinaryNoteDto> {
    public final IngredientMapper ingredientMapper = new IngredientMapper();
    public final CulinaryNoteMapper culinaryNoteMapper = new CulinaryNoteMapper();

    @Override
    public IngredientInCulinaryNoteDto map(IngredientInCulinaryNote ingredientInCulinaryNote) {
        return new IngredientInCulinaryNoteDto(
                ingredientMapper.map(ingredientInCulinaryNote.getIngredient()),
                culinaryNoteMapper.map(ingredientInCulinaryNote.getCulinaryNote()),
                ingredientInCulinaryNote.getUnitOfMeasurement(),
                ingredientInCulinaryNote.getAmount());
    }

    public IngredientInCulinaryNote map(IngredientInCulinaryNoteDto ingredientInCulinaryNoteSto) {
        return new IngredientInCulinaryNote(
                ingredientMapper.map(ingredientInCulinaryNoteSto.getIngredient()),
                culinaryNoteMapper.map(ingredientInCulinaryNoteSto.getCulinaryNote()),
                ingredientInCulinaryNoteSto.getUnitOfMeasurement(),
                ingredientInCulinaryNoteSto.getAmount());
    }
}