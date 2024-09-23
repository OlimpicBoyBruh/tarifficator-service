package ru.neoflex.jd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.neoflex.jd.dto.TariffDto;

@FeignClient(name = "product-service", url = "${integration.service.product.base-url}")
public interface ProductClient {
    @PutMapping(value = "${integration.service.product.method.update-tariff}")
    void updateTariff(@RequestBody TariffDto tariffDto, @RequestHeader("Authorization") String token);
}
