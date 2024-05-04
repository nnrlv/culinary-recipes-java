package mappers.userrating;

import dto.userrating.UserRatingDto;
import entities.UserRating;
import mappers.Mapper;

public class UserRatingMapper implements Mapper<UserRating, UserRatingDto> {
    @Override
    public UserRatingDto map(UserRating userRating) {
        return new UserRatingDto(
                userRating.getUser(),
                userRating.getCulinaryNote(),
                userRating.getGrade(),
                userRating.getComment());
    }
}