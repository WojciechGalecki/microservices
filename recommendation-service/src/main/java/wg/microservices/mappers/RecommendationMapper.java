package wg.microservices.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import wg.api.core.recommendation.Recommendation;
import wg.microservices.persistence.RecommendationEntity;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    @Mappings({
        @Mapping(target = "rate", source = "entity.rating"),
    })
    Recommendation entityToApi(RecommendationEntity entity);

    @Mappings({
        @Mapping(target = "rating", source = "api.rate"),
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "version", ignore = true)
    })
    RecommendationEntity apiToEntity(Recommendation api);
}
