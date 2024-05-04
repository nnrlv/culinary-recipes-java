package services;

import repositories.UserRatingRepository;
import dto.userrating.CreateUserRatingDto;
import entities.UserRating;
import dto.userrating.UserRatingDto;
import mappers.userrating.CreateUserRatingMapper;
import mappers.userrating.UserRatingMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRatingServiceTest {

    @Mock
    private UserRatingRepository userRatingRepository;
    @Mock
    private CreateUserRatingMapper createUserRatingMapper;
    @Mock
    private UserRatingMapper userRatingMapper;
    @InjectMocks
    private UserRatingService userRatingService;

    @Test
    void testCreateUserRating() {
        CreateUserRatingDto createUserRatingDto = new CreateUserRatingDto();
        UserRating userRating = new UserRating();
        when(createUserRatingMapper.map(createUserRatingDto)).thenReturn(userRating);
        when(userRatingRepository.create(userRating)).thenReturn(userRating);

        UserRating result = userRatingService.create(createUserRatingDto);

        assertThat(result).isEqualTo(userRating);
    }

    @Test
    void testGetAllUserRatingsByCulinaryNoteId() {
        Long idCulinaryNote = 1L;
        List<UserRating> userRatings = List.of(new UserRating());
        when(userRatingRepository.getAllByCulinaryNoteId(idCulinaryNote)).thenReturn(userRatings);
        when(userRatingMapper.map(any())).thenReturn(new UserRatingDto());

        List<UserRatingDto> result = userRatingService.getAllByCulinaryNoteId(idCulinaryNote);

        assertThat(result).hasSize(1);
    }

    @Test
    void testDeleteUserRating() {
        Long idUser = 1L;
        Long idCulinaryNote = 1L;
        when(userRatingRepository.delete(idUser, idCulinaryNote)).thenReturn(true);

        boolean result = userRatingService.delete(idUser, idCulinaryNote);

        assertThat(result).isTrue();
    }
}
