package wg.microservices.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import wg.api.core.product.Product;
import wg.microservices.persistence.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product entityToApi(ProductEntity entity);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "version", ignore = true)
    })
    ProductEntity apiToEntity(Product api);
}
