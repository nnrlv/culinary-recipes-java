package mappers.culinarynote;

import dto.culinarynote.UpdateCulinaryNoteDto;
import entities.CulinaryNote;
import mappers.Mapper;

public class UpdateCulinaryNoteMapper implements Mapper<UpdateCulinaryNoteDto, CulinaryNote> {
    @Override
    public CulinaryNote map(UpdateCulinaryNoteDto updateCulinaryNoteDto) {
        return new CulinaryNote(
                updateCulinaryNoteDto.getIdCulinaryNote(),
                null,
                null,
                updateCulinaryNoteDto.getName(),
                updateCulinaryNoteDto.getDescription(),
                updateCulinaryNoteDto.getInstructions());
    }
}