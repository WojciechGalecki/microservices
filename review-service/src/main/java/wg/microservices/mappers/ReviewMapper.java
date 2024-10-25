package wg.microservices.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import wg.api.core.review.Review;
import wg.microservices.persistence.ReviewEntity;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review entityToApi(ReviewEntity entity);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "version", ignore = true)
    })
    ReviewEntity apiToEntity(Review api);
}
