package ru.neoflex.jd.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.jd.client.TariffClient;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.entity.Product;
import ru.neoflex.jd.mapping.ProductMapping;
import ru.neoflex.jd.mapping.TariffMapper;
import ru.neoflex.jd.repository.ProductRepository;
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

    public void createProduct(ProductDto productDto) {
        log.info("Saving product: {}", productDto);
        if (productDto.getTariff() != null) {
            tariffClient.saveTariff(tariffMapper.toDto(productDto.getTariff()));
        }
        productRepository.save(productMapping.toEntity(productDto));
        log.info("Product saved");

    }

    public ProductDto deleteProductById(UUID id) {
        log.info("Deleting product with id: {}", id);
        ProductDto productDto = productMapping.toDto(productRepository.getReferenceById(id));
        log.info("Product deleted product {}", productDto);
        return productDto;
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
        log.info("Product successfully rollbacked");
    }

    public void saveProduct(Product product) {
        log.info("Invoke saveProduct with product: {}", product);
        productRepository.save(product);
        log.info("Save product successfully");
    }

    public Product getProductByIdNotDto(UUID id) {
        log.info("Invoke getProductByIdNotDto with id: {}", id);
        Product product = productRepository.findById(id).orElseThrow();
        log.info("Returned product: {}", product);
        return product;
    }
}
