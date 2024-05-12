package services;

import dto.IngredientInCulinaryNoteDto;
import entities.Category;
import entities.User;
import entities.UserRole;
import repositories.CulinaryNoteRepository;
import dto.CulinaryNoteDto;
import entities.CulinaryNote;
import mappers.CulinaryNoteMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CulinaryNoteServiceTest {

    @Mock
    private CulinaryNoteRepository culinaryNoteRepository;

    @Mock
    private CulinaryNoteMapper culinaryNoteMapper;

    @Mock
    private IngredientInCulinaryNoteService ingredientInCulinaryNoteService;

    @InjectMocks
    private CulinaryNoteService culinaryNoteService;
    @Test
    void testGetAllCulinaryNotes() {
        // Arrange
        List<CulinaryNote> culinaryNotes = new ArrayList<>();
        culinaryNotes.add(new CulinaryNote());
        culinaryNotes.add(new CulinaryNote());

        when(culinaryNoteRepository.getAll()).thenReturn(culinaryNotes);
        when(culinaryNoteMapper.map((CulinaryNote) any())).thenAnswer(invocation -> {
            CulinaryNote culinaryNote = invocation.getArgument(0);
            CulinaryNoteDto culinaryNoteDto = new CulinaryNoteDto();
            culinaryNoteDto.setIdCulinaryNote(culinaryNote.getIdCulinaryNote());
            return culinaryNoteDto;
        });

        // Act
        List<CulinaryNoteDto> result = culinaryNoteService.getAll();

        // Assert
        assertThat(result).hasSize(2);
    }

    @Test
    void testGetCulinaryNoteById() {
        // Arrange
        Long id = 1L;
        CulinaryNote culinaryNote = new CulinaryNote();
        culinaryNote.setIdCulinaryNote(id);

        when(culinaryNoteRepository.getById(id)).thenReturn(culinaryNote);
        when(culinaryNoteMapper.map(culinaryNote)).thenReturn(new CulinaryNoteDto());

        // Act
        CulinaryNoteDto result = culinaryNoteService.getById(id);

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    void testUpdateCulinaryNote() {
        // Arrange
        CulinaryNoteDto updateCulinaryNoteDto = new CulinaryNoteDto();

        when(culinaryNoteMapper.map(updateCulinaryNoteDto)).thenReturn(new CulinaryNote());
        when(culinaryNoteRepository.update(any())).thenReturn(true);

        // Act
        boolean result = culinaryNoteService.update(updateCulinaryNoteDto);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void testDeleteCulinaryNote() {
        // Arrange
        Long id = 1L;
        CulinaryNote culinaryNote = new CulinaryNote();
        CulinaryNoteDto culinaryNoteDto = new CulinaryNoteDto();
        culinaryNoteDto.setIdCulinaryNote(id);

        when(culinaryNoteRepository.getById(id)).thenReturn(culinaryNote);
        when(culinaryNoteMapper.map(culinaryNote)).thenReturn(culinaryNoteDto);
        when(ingredientInCulinaryNoteService.getAllByCulinaryNoteId(id)).thenReturn(new ArrayList<>());
        when(culinaryNoteRepository.delete(id)).thenReturn(true);

        // Act
        boolean result = culinaryNoteService.delete(id);

        // Assert
        assertThat(result).isTrue();
    }
}