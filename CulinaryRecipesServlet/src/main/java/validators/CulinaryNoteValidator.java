package validators;

import dto.CulinaryNoteDto;

public class CulinaryNoteValidator {

    public void validate(CulinaryNoteDto culinaryNoteDto) {
        if (culinaryNoteDto.getName().isEmpty())
            throw new IllegalArgumentException("Name is required");
        if (culinaryNoteDto.getName().length() < 3)
            throw new IllegalArgumentException("Name is too short");
        if (culinaryNoteDto.getDescription().isEmpty())
            throw new IllegalArgumentException("Description is required");
        if (culinaryNoteDto.getDescription().length() < 10)
            throw new IllegalArgumentException("Description is too short");
        if (culinaryNoteDto.getInstructions().isEmpty())
            throw new IllegalArgumentException("Instructions are required");
        if (culinaryNoteDto.getInstructions().length() < 10)
            throw new IllegalArgumentException("Instructions are too short");
        if (culinaryNoteDto.getUser() == null)
            throw new IllegalArgumentException("User is required");
        if (culinaryNoteDto.getCategories() == null || culinaryNoteDto.getCategories().isEmpty())
            throw new IllegalArgumentException("Categories are required");
    }
}
