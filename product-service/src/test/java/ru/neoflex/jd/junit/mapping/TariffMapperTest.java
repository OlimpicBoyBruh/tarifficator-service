package ru.neoflex.jd.junit.mapping;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.neoflex.jd.dto.TariffDto;
import ru.neoflex.jd.entity.Tariff;
import ru.neoflex.jd.mapping.TariffMapper;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TariffMapperTest {
    private final TariffMapper tariffMapper = Mappers.getMapper(TariffMapper.class);
    private static final UUID UNIVERSAL_ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5");
    private static final String TARIFF_NAME = "Giga";
    private static final String TARIFF_DESCRIPTION = "Просто крутой тариф";
    private static final double TARIFF_RATE = 15.5;
    private static final LocalDateTime UNIVERSAL_TIME = LocalDateTime
            .of(2025, 12, 12, 12, 12);

    @Test
    void toDto() {
        TariffDto tariffDto = tariffMapper.toDto(getTariff());
        assertAll(
                () -> assertEquals(UNIVERSAL_ID, tariffDto.getId()),
                () -> assertEquals(TARIFF_NAME, tariffDto.getName()),
                () -> assertEquals(TARIFF_DESCRIPTION, tariffDto.getDescription()),
                () -> assertEquals(TARIFF_RATE, tariffDto.getRate()),
                () -> assertEquals(UNIVERSAL_TIME, tariffDto.getStartDate()),
                () -> assertEquals(UNIVERSAL_TIME, tariffDto.getEndDate()),
                () -> assertEquals(UNIVERSAL_ID, tariffDto.getProductId())
        );
    }

    @Test
    void toEntity() {
        Tariff tariff = tariffMapper.toEntity(getTariffDto());
        assertAll(
                () -> assertEquals(UNIVERSAL_ID, tariff.getId()),
                () -> assertEquals(TARIFF_NAME, tariff.getName()),
                () -> assertEquals(TARIFF_DESCRIPTION, tariff.getDescription()),
                () -> assertEquals(TARIFF_RATE, tariff.getRate()),
                () -> assertEquals(UNIVERSAL_TIME, tariff.getStartDate()),
                () -> assertEquals(UNIVERSAL_TIME, tariff.getEndDate()),
                () -> assertEquals(UNIVERSAL_ID, tariff.getProductId())
        );
    }

    Tariff getTariff() {
        return Tariff.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .name("Giga")
                .startDate(LocalDateTime.of(2025, 12, 12, 12, 12))
                .endDate(LocalDateTime.of(2025, 12, 12, 12, 12))
                .description("Просто крутой тариф")
                .productId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .rate(15.5)
                .build();
    }

    TariffDto getTariffDto() {
        return TariffDto.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .name("Giga")
                .startDate(LocalDateTime.of(2025, 12, 12, 12, 12))
                .endDate(LocalDateTime.of(2025, 12, 12, 12, 12))
                .description("Просто крутой тариф")
                .productId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa5"))
                .rate(15.5)
                .build();
    }
}