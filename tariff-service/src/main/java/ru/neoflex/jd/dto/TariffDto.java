package ru.neoflex.jd.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TariffDto {
    @Schema(description = "Название тарифа", defaultValue = "Giga")
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Schema(description = "Описание тарифа", defaultValue = "Просто крутой тариф")
    private String description;
    @NotNull(message = "Не указан id продукта")
    private UUID productId;
    @Schema(description = "Ставка процентная", defaultValue = "15.5")
    private Double rate;
}
