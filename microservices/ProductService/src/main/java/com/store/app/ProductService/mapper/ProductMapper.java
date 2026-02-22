package com.store.app.ProductService.mapper;

import com.store.app.ProductService.model.ProductEntity;
import com.store.app.api.core.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "serviceAddress", ignore = true)
    })
    Product entityToApi(ProductEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    ProductEntity apiToEntity(Product api);

    List<Product> entityListToApiList(List<ProductEntity> entity);

    List<ProductEntity> apiListToEntityList(List<Product> api);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })
    void updateEntityFromApi(Product api, @MappingTarget ProductEntity entity);
}
