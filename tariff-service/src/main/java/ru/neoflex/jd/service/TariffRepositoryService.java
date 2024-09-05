package ru.neoflex.jd.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.jd.client.ProductClient;
import ru.neoflex.jd.dto.TariffDto;
import ru.neoflex.jd.entity.Tariff;
import ru.neoflex.jd.mapping.TariffMapper;
import ru.neoflex.jd.repository.TariffRepository;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TariffRepositoryService {
    private final TariffRepository tariffRepository;
    private final TariffMapper tariffMapper;
    private final ProductClient productClient;


    public void saveTariffAndUpdateProduct(TariffDto tariffDto) {
        log.info("Saving tariff: {}", tariffDto);
        Tariff tariff = tariffMapper.toEntity(tariffDto);
        tariff.setId(UUID.randomUUID());
        productClient.updateTariff(tariffDto);
        tariffRepository.save(tariff);
        log.info("Tariff successfully saved");
    }

    @Transactional
    public void updateTariff(TariffDto tariffDto) {
        try {
            Tariff tariff = tariffRepository.findById(tariffDto.getId()).orElseThrow();
            tariff = tariffMapper.toEntity(tariffDto, tariff);
            log.info("Saving update tariff: {}", tariff);
            tariffRepository.save(tariff);
            log.info("Tariff successfully updated");
            productClient.updateTariff(tariffDto);
        } catch (NoSuchElementException exception) {
            throw new NoSuchElementException("Тариф с таким id не найден.");

        }
    }

    public TariffDto deleteTariff(UUID tariffId) {
        log.info("Deleting tariff with id: {}", tariffId);
        TariffDto tariffDto = tariffMapper.toDto(tariffRepository.findById(tariffId).orElseThrow());
        tariffRepository.deleteById(tariffId);
        log.info("Tariff successfully deleted");
        return tariffDto;
    }

    public void saveTariff(TariffDto tariffDto) {
        log.info("Saving tariff: {}", tariffDto);
        Tariff tariff = tariffMapper.toEntity(tariffDto);
        tariffRepository.save(tariff);
        log.info("Tariff successfully saved");
    }
}
