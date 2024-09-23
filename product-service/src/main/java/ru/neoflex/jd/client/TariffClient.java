package ru.neoflex.jd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.neoflex.jd.dto.TariffDto;

@FeignClient(value = "tariff-service", url = "${integration.service.tariff.base-url}")
public interface TariffClient {
    @PostMapping(value = "${integration.service.tariff.method.save-tariff}")
    void saveTariff(@RequestBody TariffDto tariffDto, @RequestHeader("Authorization") String token);

    @DeleteMapping("${integration.service.tariff.method.delete-tariff}")
    TariffDto deleteTariff(@PathVariable("tariffId") String tariffId, @RequestHeader("Authorization") String token);
}
