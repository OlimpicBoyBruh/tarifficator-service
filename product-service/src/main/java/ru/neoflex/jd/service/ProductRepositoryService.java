package ru.neoflex.jd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.mapping.ProductMapping;
import ru.neoflex.jd.repository.ProductRepository;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRepositoryService {
    private final ProductRepository productRepository;
    private final ProductMapping productMapping;
    private final AuditService auditService;

    public void createProduct(ProductDto productDto) {
        log.info("Saving product: {}", productDto);
        productRepository.save(productMapping.toEntity(productDto));
        log.info("Product saved: {}", productDto);

    }

    public void deleteProductById(UUID id) {
        log.info("Deleting product with id: {}", id);
        productRepository.deleteById(id);
        log.info("Product deleted with id: {}", id);
    }

    public void updateProduct(ProductDto productDto) {
        log.info("Updating product: {}", productDto);
        productRepository.save(productMapping.toEntity(productDto));
        log.info("Product updated: {}", productDto);
    }

    public ProductDto getProductById(UUID id) {
        log.info("Getting product with id: {}", id);
        ProductDto productDto = productMapping.toDto(productRepository.findById(id).orElseThrow());
        log.info("Product got: {}", productDto);
        return productDto;
    }

    public void rollbackVersionProduct(UUID id) {
        log.info("Rollback version product with id: {}", id);
        productRepository.save(productMapping.toEntity(auditService.getPreviousRevisionProduct(id)));
        log.info("Product successfully rollbacked: {}", id);
    }
}