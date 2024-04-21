package services;

import dao.CulinaryNoteDao;
import dto.culinarynote.CulinaryNoteDto;
import dto.culinarynote.CreateCulinaryNoteDto;
import dto.culinarynote.UpdateCulinaryNoteDto;
import entities.CulinaryNote;
import lombok.AllArgsConstructor;
import mappers.culinarynote.CulinaryNoteMapper;
import mappers.culinarynote.CreateCulinaryNoteMapper;
import mappers.culinarynote.UpdateCulinaryNoteMapper;
import java.util.List;

@AllArgsConstructor
public class CulinaryNoteService {
    private final CulinaryNoteDao culinaryNoteDao;
    private final CreateCulinaryNoteMapper createCulinaryNoteMapper;
    private final CulinaryNoteMapper culinaryNoteMapper;
    private final UpdateCulinaryNoteMapper updateCulinaryNoteMapper;

    public CulinaryNote create(CreateCulinaryNoteDto createCulinaryNoteDto) {
        return culinaryNoteDao.create(createCulinaryNoteMapper.map(createCulinaryNoteDto));
    }

    public List<CulinaryNoteDto> getAll() {
        return culinaryNoteDao.getAll().stream().map(culinaryNoteMapper::map).toList();
    }

    public CulinaryNoteDto getById(Long id) {
        return culinaryNoteMapper.map(culinaryNoteDao.getById(id));
    }

    public boolean update(UpdateCulinaryNoteDto updateCulinaryNoteDto) {
        return culinaryNoteDao.update(updateCulinaryNoteMapper.map(updateCulinaryNoteDto));
    }

    public boolean delete(Long id) {
        return culinaryNoteDao.delete(id);
    }
}
