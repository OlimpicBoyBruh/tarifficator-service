package ru.neoflex.jd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.neoflex.jd.dto.TariffDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaSender {
    private final KafkaTemplate<String, TariffDto> kafkaTemplate;

    public void send(TariffDto tariffDto) {
        log.info("Sending message to Kafka: {}", tariffDto);
        kafkaTemplate.send("product-tariff", tariffDto);
    }
}
