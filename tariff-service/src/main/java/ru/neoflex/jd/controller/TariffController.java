package ru.neoflex.jd.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.jd.dto.TariffDto;
import ru.neoflex.jd.service.TariffRepositoryService;
import java.util.UUID;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/tariff")
public class TariffController {
    private final TariffRepositoryService tariffRepositoryService;

    @PostMapping("/create")
    public void createTariff(@RequestBody @Valid TariffDto tariffDto) {
        log.info("Creation Request Tariff: {}", tariffDto);
        tariffRepositoryService.saveTariffAndSendMessage(tariffDto);
    }

    @DeleteMapping("/delete/{tariffId}")
    public TariffDto deleteTariff(@PathVariable("tariffId") String tariffId) {
        log.info("Delete Tariff: {}", tariffId);
        return tariffRepositoryService.deleteTariff(UUID.fromString(tariffId));
    }

    @PutMapping("/update/{tariffId}")
    public void updateTariff(@PathVariable("tariffId") String tariffId, @RequestBody @Valid TariffDto tariffDto) {
        log.info("Update Tariff: {}", tariffId);
        tariffRepositoryService.updateTariff(UUID.fromString(tariffId), tariffDto);

    }
    /*@GetMapping("/get/{tariffId}")
    public TariffDto getTariff(@PathVariable("tariffId") String tariffId) {
        log.info("Get Tariff: {}", tariffId);
        return tariffRepositoryService.getTariff(UUID.fromString(tariffId));
    }*/

}
