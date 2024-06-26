package services;

import dto.IngredientInCulinaryNoteDto;
import repositories.IngredientInCulinaryNoteRepository;
import entities.IngredientInCulinaryNote;
import mappers.IngredientInCulinaryNoteMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientInCulinaryNoteServiceTest {

    @Mock
    private IngredientInCulinaryNoteRepository ingredientInCulinaryNoteRepository;
    @Mock
    private IngredientInCulinaryNoteMapper ingredientInCulinaryNoteMapper;
    @InjectMocks
    private IngredientInCulinaryNoteService ingredientInCulinaryNoteService;

    @Test
    void testCreateIngredientInCulinaryNote() {
        IngredientInCulinaryNoteDto createIngredientInCulinaryNoteDto = new IngredientInCulinaryNoteDto();
        IngredientInCulinaryNote ingredientInCulinaryNote = new IngredientInCulinaryNote();
        when(ingredientInCulinaryNoteMapper.map(createIngredientInCulinaryNoteDto))
                .thenReturn(ingredientInCulinaryNote);
        when(ingredientInCulinaryNoteRepository.create(ingredientInCulinaryNote)).thenReturn(ingredientInCulinaryNote);

        IngredientInCulinaryNote result = ingredientInCulinaryNoteService.create(createIngredientInCulinaryNoteDto);

        assertThat(result).isEqualTo(ingredientInCulinaryNote);
    }

    @Test
    void testGetAllIngredientInCulinaryNotesByCulinaryNoteId() {
        Long idCulinaryNote = 1L;
        List<IngredientInCulinaryNote> ingredientInCulinaryNotes = List.of(new IngredientInCulinaryNote());
        when(ingredientInCulinaryNoteRepository.getAllByCulinaryNoteId(idCulinaryNote))
                .thenReturn(ingredientInCulinaryNotes);
        when(ingredientInCulinaryNoteMapper.map(any(IngredientInCulinaryNote.class))).thenReturn(new IngredientInCulinaryNoteDto());

        List<IngredientInCulinaryNoteDto> result = ingredientInCulinaryNoteService.getAllByCulinaryNoteId(idCulinaryNote);

        assertThat(result).hasSize(1);
    }

    @Test
    void testDeleteIngredientInCulinaryNote() {
        Long idIngredient = 1L;
        Long idCulinaryNote = 1L;
        when(ingredientInCulinaryNoteRepository.delete(idIngredient, idCulinaryNote)).thenReturn(true);

        boolean result = ingredientInCulinaryNoteService.delete(idIngredient, idCulinaryNote);

        assertThat(result).isTrue();
    }
}
