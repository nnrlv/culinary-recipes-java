package services;

import entities.User;
import repositories.UserRatingRepository;
import dto.userrating.UserRatingDto;
import dto.userrating.CreateUserRatingDto;
import entities.UserRating;
import lombok.AllArgsConstructor;
import mappers.userrating.UserRatingMapper;
import mappers.userrating.CreateUserRatingMapper;
import java.util.List;

@AllArgsConstructor
public class UserRatingService {
    private final UserRatingRepository userRatingRepository;
    private final CreateUserRatingMapper createUserRatingMapper;
    private final UserRatingMapper userRatingMapper;

    public UserRating create(CreateUserRatingDto createUserRatingDto) {
        return userRatingRepository.create(createUserRatingMapper.map(createUserRatingDto));
    }

    public List<UserRatingDto> getAllByCulinaryNoteId(Long idCulinaryNote) {
        return userRatingRepository.getAllByCulinaryNoteId(idCulinaryNote).stream().map(userRatingMapper::map).toList();
    }
    public boolean delete(Long idUser, Long idCulinaryNote) {
        return userRatingRepository.delete(idUser, idCulinaryNote);
    }
}
