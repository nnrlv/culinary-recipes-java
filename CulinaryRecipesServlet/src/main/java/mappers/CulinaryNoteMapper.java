package mappers;

import dto.CulinaryNoteDto;
import entities.CulinaryNote;

public class CulinaryNoteMapper implements Mapper<CulinaryNote, CulinaryNoteDto> {
    private final UserMapper userMapper = new UserMapper();

    @Override
    public CulinaryNoteDto map(CulinaryNote culinaryNote) {
        return new CulinaryNoteDto(
                culinaryNote.getIdCulinaryNote(),
                culinaryNote.getCategories(),
                null,
                userMapper.map(culinaryNote.getUser()),
                culinaryNote.getName(),
                culinaryNote.getDescription(),
                culinaryNote.getInstructions());
    }

    public CulinaryNote map(CulinaryNoteDto culinaryNoteDto) {
        return new CulinaryNote(
                culinaryNoteDto.getIdCulinaryNote(),
                culinaryNoteDto.getCategories(),
                userMapper.map(culinaryNoteDto.getUser()),
                culinaryNoteDto.getName(),
                culinaryNoteDto.getDescription(),
                culinaryNoteDto.getInstructions());
    }
}