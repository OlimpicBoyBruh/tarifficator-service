package ru.neoflex.jd.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.jd.dto.TariffDto;
import ru.neoflex.jd.entity.Tariff;
import ru.neoflex.jd.mapping.TariffMapper;
import ru.neoflex.jd.repository.TariffRepository;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TariffRepositoryService {
    private final TariffRepository tariffRepository;
    private final TariffMapper tariffMapper;
    private final KafkaSender kafkaSender;


    public void saveTariffAndSendMessage(TariffDto tariffDto) {
        log.info("Saving tariff: {}", tariffDto);
        Tariff tariff = tariffMapper.toEntity(tariffDto);
        tariff.setId(UUID.randomUUID());
        tariffRepository.save(tariff);
        log.info("Tariff successfully saved");
        kafkaSender.send(tariffDto);
    }

    @Transactional
    public void updateTariff(UUID tariffId, TariffDto tariffDto) {
        Tariff tariff = tariffRepository.findById(tariffId).orElseThrow();
        tariff = tariffMapper.toEntity(tariffDto, tariff);
        log.info("Saving update tariff: {}", tariff);
        tariffRepository.save(tariff);
        log.info("Tariff successfully updated");
        kafkaSender.send(tariffDto);
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
