package services;

import repositories.UserRatingRepository;
import dto.UserRatingDto;
import entities.UserRating;
import lombok.AllArgsConstructor;
import mappers.UserRatingMapper;
import java.util.List;

@AllArgsConstructor
public class UserRatingService {
    private final UserRatingRepository userRatingRepository;
    private final UserRatingMapper userRatingMapper;

    public UserRating create(UserRatingDto createUserRatingDto) {
        return userRatingRepository.create(userRatingMapper.map(createUserRatingDto));
    }

    public List<UserRatingDto> getAllByCulinaryNoteId(Long idCulinaryNote) {
        return userRatingRepository.getAllByCulinaryNoteId(idCulinaryNote).stream().map(userRatingMapper::map).toList();
    }
    public boolean delete(Long idUser, Long idCulinaryNote) {
        return userRatingRepository.delete(idUser, idCulinaryNote);
    }
}
