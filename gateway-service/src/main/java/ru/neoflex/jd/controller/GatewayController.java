package ru.neoflex.jd.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.neoflex.jd.dto.ProductAudDto;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.dto.TariffDto;
import java.time.LocalDate;
import java.util.List;

@RequestMapping("/tarifficator")
public interface GatewayController {
    @GetMapping("/product/actual/{productId}")
    ProductDto getActualProduct(@PathVariable("productId") String productId,
                                @Parameter(hidden = true) @RequestHeader("Authorization") String token);


    @PostMapping("/product/create")
    void createProduct(@RequestBody ProductDto productDto,
                       @Parameter(hidden = true) @RequestHeader("Authorization") String token);

    @DeleteMapping("/product/delete/{productId}")
    ProductDto deleteProduct(@PathVariable("productId") String productId,
                             @Parameter(hidden = true) @RequestHeader("Authorization") String token);

    @GetMapping("/product/versions/{productId}")
    List<ProductAudDto> getProductVersions(@PathVariable("productId") String productId,
                                           @Parameter(hidden = true) @RequestHeader("Authorization") String token);

    @PostMapping("/tariff/create")
    void createTariff(@RequestBody TariffDto tariffDto,
                      @Parameter(hidden = true) @RequestHeader("Authorization") String token);

    @DeleteMapping("/tariff/delete/{tariffId}")
    TariffDto deleteTariff(@PathVariable("tariffId") String tariffId,
                           @Parameter(hidden = true) @RequestHeader("Authorization") String token);

    @PutMapping("/tariff/update/")
    void updateTariff(@RequestBody TariffDto tariffDto,
                      @Parameter(hidden = true) @RequestHeader("Authorization") String token);

    @GetMapping("/product/previous-version")
    List<ProductDto> getPreviousVersion(@RequestParam("productId") String productId,
                                        @RequestParam("period") LocalDate period,
                                        @Parameter(hidden = true) @RequestHeader("Authorization") String token);

    @PutMapping("/product/rollback-version/{productId}")
    void rollbackVersion(@PathVariable("productId") String productId,
                         @Parameter(hidden = true) @RequestHeader("Authorization") String token);
}
