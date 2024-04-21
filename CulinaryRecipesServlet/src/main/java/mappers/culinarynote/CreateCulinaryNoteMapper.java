package mappers.culinarynote;

import dto.culinarynote.CreateCulinaryNoteDto;
import entities.CulinaryNote;
import mappers.Mapper;

public class CreateCulinaryNoteMapper implements Mapper<CreateCulinaryNoteDto, CulinaryNote> {
    @Override
    public CulinaryNote map(CreateCulinaryNoteDto createCulinaryNoteDto) {
        return new CulinaryNote(
                null,
                null,
                createCulinaryNoteDto.getUser(),
                createCulinaryNoteDto.getName(),
                createCulinaryNoteDto.getDescription(),
                createCulinaryNoteDto.getInstructions());
    }
}