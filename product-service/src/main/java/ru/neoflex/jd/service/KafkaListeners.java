package ru.neoflex.jd.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.neoflex.jd.dto.TariffDto;
import ru.neoflex.jd.entity.Product;
import ru.neoflex.jd.mapping.TariffMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaListeners {
    private final ProductRepositoryService productRepositoryService;
    private final TariffMapper tariffMapper;

    @Transactional
    @KafkaListener(topics = "product-tariff", groupId = "tarifficatorGroup")
    public void saveTariff(TariffDto tariffDto) {
        log.info("Received tariff: {}", tariffDto);
        try {
            Product product = productRepositoryService.getProductByIdNotDto(tariffDto.getProductId());
            product.setTariff(tariffMapper.toEntity(tariffDto));
            productRepositoryService.saveProduct(product);
            log.info("Product saved: {}", product);
        } catch (Exception e) {
            log.error("Error while saving tariff", e);
        }

    }
}

