package ru.neoflex.jd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.jd.client.ProductClient;
import ru.neoflex.jd.client.TariffClient;
import ru.neoflex.jd.dto.ProductAudDto;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.dto.TariffDto;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayService {

    private final ProductClient productClient;
    private final TariffClient tariffClient;

    public ProductDto getActualProduct(String productId) {
        log.info("Get Actual Product: {}", productId);
        ProductDto productDto = productClient.getActualPreviousProduct(productId);
        log.info("Return actual productDto: {}", productDto);
        return productDto;
    }

    public void createProduct(ProductDto productDto) {
        log.info("Create Product: {}", productDto);
        productClient.createProduct(productDto);
        log.info("Product created");
    }

    public ProductDto deleteProduct(String productId) {
        log.info("Delete Product: {}", productId);
        ProductDto productDto = productClient.deleteProduct(productId);
        log.info("Product deleted productDto: {}", productDto);
        return productDto;
    }

    public List<ProductAudDto> getProductVersions(String productId) {
        log.info("Get Product Versions: {}", productId);
        List<ProductAudDto> productAudDtos = productClient.getRevisionsVersion(productId);
        log.info("Return product versions: {}", productAudDtos);
        return productAudDtos;
    }

    public void createTariff(TariffDto tariffDto) {
        log.info("Create Tariff: {}", tariffDto);
        tariffClient.createTariff(tariffDto);
        log.info("Tariff created");
    }

    public TariffDto deleteTariff(String tariffId) {
        log.info("Delete Tariff: {}", tariffId);
        TariffDto tariffDto = tariffClient.deleteTariff(tariffId);
        log.info("Tariff deleted tariffDto: {}", tariffDto);
        return tariffDto;

    }

    public void updateTariff(String tariffId, TariffDto tariffDto) {
        log.info("Update Tariff: {}", tariffId);
        tariffClient.updateTariff(tariffId, tariffDto);
        log.info("Tariff updated");
    }

    public List<ProductDto> getPreviousVersion(String productId, LocalDate period) {
        log.info("Get Previous Version: {}", productId);
        List<ProductDto> productDtos = productClient.getPreviousVersion(productId, period);
        log.info("Return previous version: {}", productDtos);
        return productDtos;
    }

    public void rollbackVersion(String productId) {
        log.info("Rollback Version: {}", productId);
        productClient.rollbackVersion(productId);
        log.info("Version rolled back");
    }
}
