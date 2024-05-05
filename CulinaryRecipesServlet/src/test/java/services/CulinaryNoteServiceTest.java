package services;

import repositories.CulinaryNoteRepository;
import dto.CulinaryNoteDto;
import entities.CulinaryNote;
import mappers.CulinaryNoteMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CulinaryNoteServiceTest {

    @Mock
    private CulinaryNoteRepository culinaryNoteRepository;
    @Mock
    private CulinaryNoteMapper culinaryNoteMapper;
    @InjectMocks
    private CulinaryNoteService culinaryNoteService;

    @Test
    void testCreateCulinaryNote() {
        CulinaryNoteDto createCulinaryNoteDto = new CulinaryNoteDto();
        CulinaryNote culinaryNote = new CulinaryNote();
        when(culinaryNoteMapper.map(createCulinaryNoteDto)).thenReturn(culinaryNote);
        when(culinaryNoteRepository.create(culinaryNote)).thenReturn(culinaryNote);

        CulinaryNoteDto result = culinaryNoteService.create(createCulinaryNoteDto);

        assertThat(result).isEqualTo(culinaryNote);
    }

    @Test
    void testGetAllCulinaryNotes() {
        List<CulinaryNote> culinaryNotes = List.of(new CulinaryNote());
        when(culinaryNoteRepository.getAll()).thenReturn(culinaryNotes);
        when(culinaryNoteMapper.map(any(CulinaryNote.class))).thenReturn(new CulinaryNoteDto());

        List<CulinaryNoteDto> result = culinaryNoteService.getAll();

        assertThat(result).hasSize(1);
    }

    @Test
    void testGetCulinaryNoteById() {
        Long id = 1L;
        CulinaryNote culinaryNote = new CulinaryNote();
        CulinaryNoteDto culinaryNoteDto = new CulinaryNoteDto();
        when(culinaryNoteRepository.getById(id)).thenReturn(culinaryNote);
        when(culinaryNoteMapper.map(culinaryNote)).thenReturn(culinaryNoteDto);

        CulinaryNoteDto result = culinaryNoteService.getById(id);

        assertThat(result).isEqualTo(culinaryNoteDto);
    }

    @Test
    void testUpdateCulinaryNote() {
        CulinaryNoteDto updateCulinaryNoteDto = new CulinaryNoteDto();
        CulinaryNote culinaryNote = new CulinaryNote();
        when(culinaryNoteMapper.map(updateCulinaryNoteDto)).thenReturn(culinaryNote);
        when(culinaryNoteRepository.update(culinaryNote)).thenReturn(true);

        boolean result = culinaryNoteService.update(updateCulinaryNoteDto);

        assertThat(result).isTrue();
    }

    @Test
    void testDeleteCulinaryNote() {
        Long id = 1L;
        when(culinaryNoteRepository.delete(id)).thenReturn(true);

        boolean result = culinaryNoteService.delete(id);

        assertThat(result).isTrue();
    }
}
