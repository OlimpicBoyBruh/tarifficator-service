package ru.neoflex.jd.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.jd.dto.ProductAudDto;
import ru.neoflex.jd.dto.ProductDto;
import ru.neoflex.jd.entity.Product;
import ru.neoflex.jd.mapping.ProductAudMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AuditService {

    private final EntityManager entityManager;
    private final ProductAudMapper productAudMapper;

    public List<ProductAudDto> getAllRevisionProduct(UUID productId) {
        log.info("Getting all revision,By productId:{}", productId);
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Number> revisions = reader.getRevisions(Product.class, productId);

        List<ProductAudDto> result = IntStream.range(0, revisions.size())
                .mapToObj(i -> {
                    Number revision = revisions.get(i);
                    Product product = reader.find(Product.class, productId, revision);
                    ProductAudDto dto = productAudMapper.toAudDto(product);
                    if (dto != null) {
                        dto.setVersion((long) (i + 1));
                    }
                    return dto;
                })
                .filter(Objects::nonNull)
                .toList();
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Нет версий продукта");
        }
        log.info("Found products, returning {}", result);
        return result;
    }

    public ProductDto getPreviousRevisionProduct(UUID productId) {
        log.info("Getting previous revision,By productId:{}", productId);
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Number> revisions = reader.getRevisions(Product.class, productId);
        if (revisions.size() < 2) {
            throw new IllegalArgumentException("Нет других версий продукта");
        }
        Number revision = revisions.get(revisions.size() - 2);
        Product product = reader.find(Product.class, productId, revision);
        log.info("Found product, returning {}", product);
        return productAudMapper.toDto(product);
    }

    public List<ProductDto> getVersionProductForPeriod(UUID productId, LocalDate period) {
        log.info("Getting products for period {},By productId:{}", period, productId);
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Number> revisions = reader.getRevisions(Product.class, productId);
        List<Product> productsForPeriod = revisions.stream()
                .map(revision -> reader.find(Product.class, productId, revision))
                .filter(Objects::nonNull)
                .filter(product -> product.getStartDate() != null && LocalDate.from(product.getStartDate()).equals(period))
                .toList();
        if (productsForPeriod.isEmpty()) {
            throw new IllegalArgumentException("Нет продуктов за указанный период");
        }
        log.info("Found {} products, returning {}", productsForPeriod.size(), productsForPeriod);
        return productsForPeriod.stream().map(productAudMapper::toDto).toList();
    }


}
