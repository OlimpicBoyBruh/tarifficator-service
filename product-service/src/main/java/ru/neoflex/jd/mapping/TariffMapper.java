package ru.neoflex.jd.mapping;

import org.mapstruct.Mapper;
import ru.neoflex.jd.dto.TariffDto;
import ru.neoflex.jd.entity.Tariff;

@Mapper(componentModel = "spring")
public interface TariffMapper {
    TariffDto toDto(Tariff tariff);

    Tariff toEntity(TariffDto tariffDto);
}
