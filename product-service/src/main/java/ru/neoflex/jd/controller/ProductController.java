package ru.neoflex.jd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.jd.dto.ProductAudDto;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.dto.TariffDto;
import ru.neoflex.jd.service.AuditService;
import ru.neoflex.jd.service.ProductRepositoryService;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Tag(name = "Product service", description = "Сервис для создания/управления/удаления продуктов.")
public class ProductController {
    private final AuditService auditService;
    private final ProductRepositoryService productRepositoryService;

    @Operation(summary = "Создание продукта.",
            description = "Поступает запрос в формате JSON, десериализация  в ProductDto, после происходит запрос" +
                    " на добавление в БД, если уже заполнено поле Tariff, отправляется запрос на добавление в сервис Tariff")

    @PostMapping("/create")
    public void createProduct(@RequestBody @Valid ProductDto productDto) {
        log.info("Create product: {}", productDto);
        productRepositoryService.createProduct(productDto);
        log.info("Product created");
    }

    @Operation(summary = "Удаление продукта.",
            description = "Поступает запрос в формате JSON, десериализация  в ProductDto, после происходит удаление из БД")
    @DeleteMapping("/delete/{productId}")
    public ProductDto deleteProduct(@PathVariable("productId") String productId) {
        log.info("Delete product: {}", productId);
        ProductDto productDto = productRepositoryService.deleteProductById(UUID.fromString(productId));
        log.info("Product deleted");
        return productDto;
    }

    @GetMapping("/{productId}")
    public ProductDto getActualPreviousProduct(@PathVariable("productId") String productId) {
        log.info("Get product: {}", productId);
        return productRepositoryService.getProductById(UUID.fromString(productId));
    }

    @GetMapping("/previous-version/all/{productId}")
    public List<ProductAudDto> getRevisionsVersion(@PathVariable("productId") String productId) {
        log.info("Get all previous version product: {}", productId);
        return auditService.getAllRevisionProduct(UUID.fromString(productId));
    }

    @GetMapping("/previous-version")
    public List<ProductDto> getPreviousVersionForPeriod(@RequestParam("productId") String productId,
                                                        @RequestParam("period") LocalDate period) {
        log.info("Get previous version product: {}", productId);
        return auditService.getVersionProductForPeriod(UUID.fromString(productId), period);

    }

    @PutMapping("/update/rollback-version/{productId}")
    public void rollbackVersion(@PathVariable("productId") String productId) {
        log.info("Rollback previous version product: {}", productId);
        productRepositoryService.rollbackVersionProduct(UUID.fromString(productId));
        log.info("Version rolled back");
    }

    @PutMapping("/update-tariff")
    public void updateTariff(@RequestBody @Valid TariffDto tariffDto) {
        log.info("Invoke updateTariff: {}", tariffDto);
        productRepositoryService.updateTariff(tariffDto);
        log.info("Tariff updated");
    }

}
