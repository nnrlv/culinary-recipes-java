package mappers.ingredientinculinarynote;

import dto.ingredientinculinarynote.CreateIngredientInCulinaryNoteDto;
import entities.IngredientInCulinaryNote;
import mappers.Mapper;

public class CreateIngredientInCulinaryNoteMapper implements Mapper<CreateIngredientInCulinaryNoteDto,
        IngredientInCulinaryNote> {
    @Override
    public IngredientInCulinaryNote map(CreateIngredientInCulinaryNoteDto createIngredientInCulinaryNoteDto) {
        return new IngredientInCulinaryNote(
                createIngredientInCulinaryNoteDto.getIngredient(),
                createIngredientInCulinaryNoteDto.getCulinaryNote(),
                createIngredientInCulinaryNoteDto.getUnitOfMeasurement(),
                createIngredientInCulinaryNoteDto.getAmount());
    }
}