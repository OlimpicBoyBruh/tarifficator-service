package ru.neoflex.jd.mapping;

import org.mapstruct.Mapper;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.entity.Product;


@Mapper(componentModel = "spring")
public interface ProductMapping {
    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDTO);
}
