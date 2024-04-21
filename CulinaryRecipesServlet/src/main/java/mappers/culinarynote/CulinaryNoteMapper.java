package mappers.culinarynote;

import dto.culinarynote.CulinaryNoteDto;
import entities.CulinaryNote;
import mappers.Mapper;

public class CulinaryNoteMapper implements Mapper<CulinaryNote, CulinaryNoteDto> {
    @Override
    public CulinaryNoteDto map(CulinaryNote culinaryNote) {
        return new CulinaryNoteDto(
                culinaryNote.getIdCulinaryNote(),
                culinaryNote.getCategories(),
                culinaryNote.getUser(),
                culinaryNote.getName(),
                culinaryNote.getDescription(),
                culinaryNote.getInstructions());
    }
}