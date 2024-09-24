package ru.neoflex.jd.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.jd.client.AuthService;
import ru.neoflex.jd.client.TariffClient;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.dto.TariffDto;
import ru.neoflex.jd.entity.Product;
import ru.neoflex.jd.exception.NotAccessException;
import ru.neoflex.jd.mapping.ProductMapping;
import ru.neoflex.jd.mapping.TariffMapper;
import ru.neoflex.jd.repository.ProductRepository;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRepositoryService {
    private final ProductRepository productRepository;
    private final ProductMapping productMapping;
    private final AuditService auditService;
    private final TariffClient tariffClient;
    private final TariffMapper tariffMapper;
    private final AuthService authService;

    public void createProduct(ProductDto productDto, String token) {
        if (Boolean.TRUE.equals(authService.validateToken(token))) {
            log.info("Saving product: {}", productDto);
            if (productDto.getTariff() != null) {
                tariffClient.saveTariff(tariffMapper.toDto(tariffMapper.toEntity(productDto.getTariff())), token);
            }
            productRepository.save(productMapping.toEntity(productDto));
            log.info("Product saved");
        }
    }

    @Transactional
    public ProductDto deleteProductById(UUID id, String token) {
        if (Boolean.TRUE.equals(authService.validateToken(token))) {
            log.info("Deleting product with id: {}", id);
            Product product = productRepository.findById(id).orElseThrow();
            if (product.getTariff() != null) {
                try {
                    tariffClient.deleteTariff(product.getTariff().getId().toString(), token);
                } catch (FeignException exception) {
                    log.error("Ошибка при удалении тарифа: {}", exception.getMessage());
                }
            }
            productRepository.deleteById(id);
            log.info("Product deleted product {}", product);
            return productMapping.toDto(product);
        }
        throw new NotAccessException("Токен неверный");
    }

    @Transactional
    public void updateTariff(TariffDto tariffDto, String token) {
        if (Boolean.TRUE.equals(authService.validateToken(token))) {
            try {
                Product product = productRepository.findById(tariffDto.getProductId()).orElseThrow();
                log.info("Updating product: {}", product);
                product.setTariff(tariffMapper.toEntity(tariffDto));
                productRepository.save(product);
            } catch (NoSuchElementException exception) {
                throw new NoSuchElementException("Продукт с таким id не найден");
            }
            log.info("Update product successfully");
        }
    }

    public ProductDto getProductById(UUID id, String token) {
        if (Boolean.TRUE.equals(authService.validateToken(token))) {
            log.info("Getting product with id: {}", id);
            ProductDto productDto = productMapping.toDto(productRepository.findById(id).orElseThrow());
            log.info("Product got: {}", productDto);
            return productDto;
        }
        throw new NotAccessException("Токен неверный");
    }

    public void rollbackVersionProduct(UUID id, String token) {
        if (Boolean.TRUE.equals(authService.validateToken(token))) {
            log.info("Rollback version product with id: {}", id);
            productRepository.save(productMapping.toEntity(auditService.getPreviousRevisionProduct(id)));
            log.info("Product successfully rolled back");
        }
    }
}
