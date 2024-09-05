package ru.neoflex.jd.controller;

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
import ru.neoflex.jd.service.GatewayService;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tarifficator")
public class GatewayController {
    private final GatewayService gatewayService;

    @GetMapping("/product/actual/{productId}")
    public ProductDto getActualProduct(@PathVariable("productId") String productId) {
        log.info("Invoke getActualProduct method with productId: {}", productId);
        return gatewayService.getActualProduct(productId);
    }

    @PostMapping("/product/create")
    public void createProduct(@RequestBody ProductDto productDto) {
        log.info("Invoke createProduct method with productDto: {}", productDto);
        gatewayService.createProduct(productDto);
    }

    @DeleteMapping("/product/delete/{productId}")
    public ProductDto deleteProduct(@PathVariable("productId") String productId) {
        log.info("Invoke deleteProduct method with productId: {}", productId);
        return gatewayService.deleteProduct(productId);
    }

    @GetMapping("/product/versions/{productId}")
    public List<ProductAudDto> getProductVersions(@PathVariable("productId") String productId) {
        log.info("Invoke getProductVersions method with productId: {}", productId);
        return gatewayService.getProductVersions(productId);
    }

    @PostMapping("/tariff/create")
    public void createTariff(@RequestBody TariffDto tariffDto) {
        log.info("Invoke createTariff method with tariffDto: {}", tariffDto);
        gatewayService.createTariff(tariffDto);
    }

    @DeleteMapping("/tariff/delete/{tariffId}")
    public TariffDto deleteTariff(@PathVariable("tariffId") String tariffId) {
        log.info("Invoke deleteTariff method with tariffId: {}", tariffId);
        return gatewayService.deleteTariff(tariffId);
    }

    @PutMapping("/tariff/update/{tariffId}")
    public void updateTariff(@PathVariable("tariffId") String tariffId, @RequestBody TariffDto tariffDto) {
        log.info("Invoke updateTariff method with tariffDto: {}", tariffDto);
        gatewayService.updateTariff(tariffId, tariffDto);
    }

    @GetMapping("/product/previous-version")
    public List<ProductDto> getPreviousVersion(@RequestParam("productId") String productId,
                                               @RequestParam("period") LocalDate period) {
        log.info("Invoke getPreviousVersion method with productId: {}, period: {}", productId, period);
        return gatewayService.getPreviousVersion(productId, period);
    }

    @PutMapping("/product/rollback-version/{productId}")
    public void rollbackVersion(@PathVariable("productId") String productId) {
        log.info("Invoke rollbackVersion method productId: {}", productId);
        gatewayService.rollbackVersion(productId);
    }
}
