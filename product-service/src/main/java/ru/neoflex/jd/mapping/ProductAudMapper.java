package ru.neoflex.jd.mapping;

import org.mapstruct.Mapper;
import ru.neoflex.jd.dto.ProductAudDto;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductAudMapper {

    ProductAudDto toAudDto(Product product);

    ProductDto toDto(Product product);
}
