package ru.neoflex.jd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.neoflex.jd.dto.ProductAudDto;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.dto.TariffDto;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Product service", description = "Сервис для создания/управления/удаления продуктов.")
public interface ProductControllerSwagger {
    @Operation(summary = "Создание продукта.",
            description = "Поступает запрос в формате JSON, десериализация  в ProductDto, после происходит запрос" +
                    " на добавление в БД, если уже заполнено поле Tariff, отправляется запрос на добавление в сервис Tariff")
    void createProduct(@RequestBody @Valid ProductDto productDto,
                       @Parameter(hidden = true) @RequestHeader("Authorization") String token);


    @Operation(summary = "Удаление продукта.",
            description = "Поступает запрос в формате JSON, десериализация  в ProductDto, после происходит удаление из БД")
    ProductDto deleteProduct(@PathVariable("productId") String productId,
                             @Parameter(hidden = true) @RequestHeader("Authorization") String token);

    @Operation(summary = "Получение актуальной версии продукта.",
            description = "Запрос по ID  продукта к базе данных для получений актуальной версии.")
    ProductDto getActualPreviousProduct(@PathVariable("productId") String productId,
                                        @RequestHeader("Authorization") @Parameter(hidden = true) String token);

    @Operation(summary = "Получение версий продукта.",
            description = "Запрос по ID  продукта к базе данных предыдущих версий продукта.")
    List<ProductAudDto> getRevisionsVersion(@PathVariable("productId") String productId,
                                            @RequestHeader("Authorization") @Parameter(hidden = true) String token);

    @Operation(summary = "Получение версий продукта за определенную дату.",
            description = "Запрос по ID  продукта к базе данных предыдущих версий продукта за определенный день.")
    List<ProductDto> getPreviousVersionForPeriod(@RequestParam("productId") String productId,
                                                 @RequestParam("period") LocalDate period,
                                                 @Parameter(hidden = true) @RequestHeader("Authorization") String token);

    @Operation(summary = "Откатить продукт до предыдущей версии.",
            description = "Запрос по ID  продукта к базе данных предыдущих версий продукта и производит откат.")
    void rollbackVersion(@PathVariable("productId") String productId,
                         @Parameter(hidden = true) @RequestHeader("Authorization") String token);

    @Operation(summary = "Обновить тариф продукта.",
            description = "Запрос по ID  продукта к базе данных для обновления тарифа продукта.")
    void updateTariff(@RequestBody @Valid TariffDto tariffDto,
                      @Parameter(hidden = true) @RequestHeader("Authorization") String token);
}
