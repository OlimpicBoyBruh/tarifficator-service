package ru.neoflex.jd.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public void createTariff(@RequestBody @Valid TariffDto tariffDto,
                             @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        log.info("Creation Request Tariff: {}", tariffDto);
        tariffRepositoryService.saveTariffAndUpdateProduct(tariffDto, token);
    }

    @DeleteMapping("/delete/{tariffId}")
    public TariffDto deleteTariff(@PathVariable("tariffId") String tariffId,
                                  @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        log.info("Delete Tariff: {}", tariffId);
        return tariffRepositoryService.deleteTariff(UUID.fromString(tariffId), token);
    }

    @PutMapping("/update")
    public void updateTariff(@RequestBody @Valid TariffDto tariffDto,
                             @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        log.info("Update Tariff: {}", tariffDto.getId());
        tariffRepositoryService.updateTariff(tariffDto, token);

    }

    @PostMapping("/integration/save")
    public void saveTariff(@RequestBody TariffDto tariffDto,
                           @Parameter(hidden = true) @RequestHeader("Authorization") String token) {
        log.info("Integration Request save Tariff: {}", tariffDto);
        tariffRepositoryService.saveTariff(tariffDto, token);
    }

}
