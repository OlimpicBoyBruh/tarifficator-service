package ru.neoflex.jd.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.neoflex.jd.dto.TariffDto;

@FeignClient(value = "tariff-service", url = "${integration.service.tariff.base-url}")
public interface TariffClient {
    @PostMapping("${integration.service.tariff.method.create}")
    void createTariff(@RequestBody @Valid TariffDto tariffDto, @RequestHeader("Authorization") String token);

    @DeleteMapping("${integration.service.tariff.method.delete}")
    TariffDto deleteTariff(@PathVariable("tariffId") String tariffId, @RequestHeader("Authorization") String token);

    @PutMapping("${integration.service.tariff.method.update}")
    void updateTariff(@RequestBody @Valid TariffDto tariffDto,
                      @RequestHeader("Authorization") String token);
}
