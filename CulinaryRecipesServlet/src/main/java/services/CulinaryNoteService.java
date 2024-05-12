package services;

import dto.IngredientInCulinaryNoteDto;
import entities.CulinaryNote;
import repositories.CulinaryNoteRepository;
import dto.CulinaryNoteDto;
import lombok.AllArgsConstructor;
import mappers.CulinaryNoteMapper;

import java.util.List;

@AllArgsConstructor
public class CulinaryNoteService {
    private final CulinaryNoteRepository culinaryNoteRepository;
    private final CulinaryNoteMapper culinaryNoteMapper;
    private final IngredientInCulinaryNoteService ingredientInCulinaryNoteService;

    public CulinaryNoteDto create(CulinaryNoteDto createCulinaryNoteDto) {
        return culinaryNoteMapper.map(culinaryNoteRepository.create(culinaryNoteMapper.map(createCulinaryNoteDto)));
    }

    public List<CulinaryNoteDto> getAll() {
        return culinaryNoteRepository.getAll().stream().map(culinaryNoteMapper::map).toList();
    }

    public CulinaryNoteDto getById(Long id) {
        return culinaryNoteMapper.map(culinaryNoteRepository.getById(id));
    }

    public boolean update(CulinaryNoteDto updateCulinaryNoteDto) {
        return culinaryNoteRepository.update(culinaryNoteMapper.map(updateCulinaryNoteDto));
    }

    public boolean delete(Long id) {
        CulinaryNote culinaryNote = culinaryNoteRepository.getById(id);
        CulinaryNoteDto culinaryNoteDto = culinaryNoteMapper.map(culinaryNote);
        for (IngredientInCulinaryNoteDto ingredient : ingredientInCulinaryNoteService
                .getAllByCulinaryNoteId(culinaryNoteDto.getIdCulinaryNote()))
        {
            ingredientInCulinaryNoteService.delete(ingredient.getIngredient().getIdIngredient(),
                    culinaryNoteDto.getIdCulinaryNote());
        }
        return culinaryNoteRepository.delete(id);
    }
}
