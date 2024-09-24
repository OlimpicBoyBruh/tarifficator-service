package ru.neoflex.jd.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.jd.dto.ProductAudDto;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.dto.TariffDto;
import ru.neoflex.jd.service.GatewayService;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GatewayControllerImpl implements GatewayController {
    private final GatewayService gatewayService;

    public ProductDto getActualProduct(String productId, String token) {
        log.info("Invoke getActualProduct method with productId: {}", productId);
        return gatewayService.getActualProduct(productId, token);
    }

    public void createProduct(ProductDto productDto, String token) {
        log.info("Invoke createProduct method with productDto: {}", productDto);
        gatewayService.createProduct(productDto, token);
    }

    public ProductDto deleteProduct(String productId, String token) {
        log.info("Invoke deleteProduct method with productId: {}", productId);
        return gatewayService.deleteProduct(productId, token);
    }

    public List<ProductAudDto> getProductVersions(String productId, String token) {
        log.info("Invoke getProductVersions method with productId: {}", productId);
        return gatewayService.getProductVersions(productId, token);
    }

    public void createTariff(TariffDto tariffDto, String token) {
        log.info("Invoke createTariff method with tariffDto: {}", tariffDto);
        gatewayService.createTariff(tariffDto, token);
    }

    public TariffDto deleteTariff(String tariffId, String token) {
        log.info("Invoke deleteTariff method with tariffId: {}", tariffId);
        return gatewayService.deleteTariff(tariffId, token);
    }

    public void updateTariff(TariffDto tariffDto, String token) {
        log.info("Invoke updateTariff method with tariffDto: {}", tariffDto);
        gatewayService.updateTariff(tariffDto, token);
    }

    public List<ProductDto> getPreviousVersion(String productId, LocalDate period, String token) {
        log.info("Invoke getPreviousVersion method with productId: {}, period: {}", productId, period);
        return gatewayService.getPreviousVersion(productId, period, token);
    }

    public void rollbackVersion(String productId, String token) {
        log.info("Invoke rollbackVersion method productId: {}", productId);
        gatewayService.rollbackVersion(productId, token);
    }
}
