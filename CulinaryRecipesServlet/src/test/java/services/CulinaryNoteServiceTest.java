package services;

import dao.CulinaryNoteDao;
import dto.culinarynote.CreateCulinaryNoteDto;
import dto.culinarynote.CulinaryNoteDto;
import dto.culinarynote.UpdateCulinaryNoteDto;
import entities.CulinaryNote;
import mappers.culinarynote.CreateCulinaryNoteMapper;
import mappers.culinarynote.CulinaryNoteMapper;
import mappers.culinarynote.UpdateCulinaryNoteMapper;
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
    private CulinaryNoteDao culinaryNoteDao;
    @Mock
    private CreateCulinaryNoteMapper createCulinaryNoteMapper;
    @Mock
    private CulinaryNoteMapper culinaryNoteMapper;
    @Mock
    private UpdateCulinaryNoteMapper updateCulinaryNoteMapper;
    @InjectMocks
    private CulinaryNoteService culinaryNoteService;

    @Test
    void testCreateCulinaryNote() {
        CreateCulinaryNoteDto createCulinaryNoteDto = new CreateCulinaryNoteDto();
        CulinaryNote culinaryNote = new CulinaryNote();
        when(createCulinaryNoteMapper.map(createCulinaryNoteDto)).thenReturn(culinaryNote);
        when(culinaryNoteDao.create(culinaryNote)).thenReturn(culinaryNote);

        CulinaryNote result = culinaryNoteService.create(createCulinaryNoteDto);

        assertThat(result).isEqualTo(culinaryNote);
    }

    @Test
    void testGetAllCulinaryNotes() {
        List<CulinaryNote> culinaryNotes = List.of(new CulinaryNote());
        when(culinaryNoteDao.getAll()).thenReturn(culinaryNotes);
        when(culinaryNoteMapper.map(any())).thenReturn(new CulinaryNoteDto());

        List<CulinaryNoteDto> result = culinaryNoteService.getAll();

        assertThat(result).hasSize(1);
    }

    @Test
    void testGetCulinaryNoteById() {
        Long id = 1L;
        CulinaryNote culinaryNote = new CulinaryNote();
        CulinaryNoteDto culinaryNoteDto = new CulinaryNoteDto();
        when(culinaryNoteDao.getById(id)).thenReturn(culinaryNote);
        when(culinaryNoteMapper.map(culinaryNote)).thenReturn(culinaryNoteDto);

        CulinaryNoteDto result = culinaryNoteService.getById(id);

        assertThat(result).isEqualTo(culinaryNoteDto);
    }

    @Test
    void testUpdateCulinaryNote() {
        UpdateCulinaryNoteDto updateCulinaryNoteDto = new UpdateCulinaryNoteDto();
        CulinaryNote culinaryNote = new CulinaryNote();
        when(updateCulinaryNoteMapper.map(updateCulinaryNoteDto)).thenReturn(culinaryNote);
        when(culinaryNoteDao.update(culinaryNote)).thenReturn(true);

        boolean result = culinaryNoteService.update(updateCulinaryNoteDto);

        assertThat(result).isTrue();
    }

    @Test
    void testDeleteCulinaryNote() {
        Long id = 1L;
        when(culinaryNoteDao.delete(id)).thenReturn(true);

        boolean result = culinaryNoteService.delete(id);

        assertThat(result).isTrue();
    }
}
