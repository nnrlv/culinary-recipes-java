package mappers;

import dto.UserRatingDto;
import entities.UserRating;

public class UserRatingMapper implements Mapper<UserRating, UserRatingDto> {
    @Override
    public UserRatingDto map(UserRating userRating) {
        return new UserRatingDto(
                userRating.getUser(),
                userRating.getCulinaryNote(),
                userRating.getGrade(),
                userRating.getComment());
    }

    public UserRating map(UserRatingDto userRatingDto) {
        return new UserRating(
                userRatingDto.getUser(),
                userRatingDto.getCulinaryNote(),
                userRatingDto.getGrade(),
                userRatingDto.getComment());
    }
}