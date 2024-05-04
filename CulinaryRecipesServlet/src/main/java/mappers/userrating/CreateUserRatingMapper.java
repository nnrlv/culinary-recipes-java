package mappers.userrating;

import dto.userrating.CreateUserRatingDto;
import entities.UserRating;
import mappers.Mapper;

public class CreateUserRatingMapper implements Mapper<CreateUserRatingDto, UserRating> {
    @Override
    public UserRating map(CreateUserRatingDto createUserRatingDto) {
        return new UserRating(
                createUserRatingDto.getUser(),
                createUserRatingDto.getCulinaryNote(),
                createUserRatingDto.getGrade(),
                createUserRatingDto.getComment());
    }
}