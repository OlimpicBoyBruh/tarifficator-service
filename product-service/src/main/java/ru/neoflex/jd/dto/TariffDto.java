package ru.neoflex.jd.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TariffDto {
    @NotNull(message = "Id тарифа не должно быть null")
    @Schema(description = "ID", defaultValue = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "Название тарифа", defaultValue = "Giga")
    private String name;
    @Schema(description = "Дата начала действия тарифа", defaultValue = "2024-08-08T00:00:00")
    private LocalDateTime startDate;
    @Schema(description = "Дата окончания действия тарифа", defaultValue = "2029-08-08T00:00:00")
    private LocalDateTime endDate;
    @Schema(description = "Описание тарифа", defaultValue = "Просто крутой тариф")
    private String description;
    @NotNull(message = "Не указан id продукта")
    @Schema(description = "ID", defaultValue = "3fa85f64-5717-4562-b3fc-2c963f66afa5")
    private UUID productId;
    @Schema(description = "Ставка процентная", defaultValue = "15.5")
    private Double rate;
}
