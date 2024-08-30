package ru.neoflex.jd.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.jd.dto.TariffDto;
import ru.neoflex.jd.entity.Tariff;

@Mapper(componentModel = "spring")
public interface TariffMapper {

    Tariff toEntity(TariffDto dto);

    TariffDto toDto(Tariff entity);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "dto.name", target = "name")
    @Mapping(source = "dto.startDate", target = "startDate")
    @Mapping(source = "dto.endDate", target = "endDate")
    @Mapping(source = "dto.description", target = "description")
    @Mapping(source = "dto.rate", target = "rate")
    @Mapping(source = "dto.productId", target = "productId")
    Tariff toEntity(TariffDto dto, Tariff entity);
}
