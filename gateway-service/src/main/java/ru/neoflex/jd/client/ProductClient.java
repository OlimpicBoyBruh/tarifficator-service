package ru.neoflex.jd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.neoflex.jd.dto.ProductAudDto;
import ru.neoflex.jd.dto.ProductDto;
import java.time.LocalDate;
import java.util.List;

@FeignClient(value = "product-service", url = "${integration.service.product.base-url}")
public interface ProductClient {
    @PostMapping("${integration.service.product.method.create}")
    void createProduct(@RequestBody ProductDto productDto);

    @DeleteMapping("${integration.service.product.method.delete}")
    ProductDto deleteProduct(@PathVariable("productId") String productId);

    @GetMapping("${integration.service.product.method.get-actual-version}")
    ProductDto getActualPreviousProduct(@PathVariable("productId") String productId);

    @GetMapping("${integration.service.product.method.get-previous-version-all}")
    List<ProductAudDto> getRevisionsVersion(@PathVariable("productId") String productId);

    @GetMapping("${integration.service.product.method.get-previous-version}")
    List<ProductDto> getPreviousVersion(@RequestParam("productId") String productId,
                                        @RequestParam("period") LocalDate period);

    @PutMapping("${integration.service.product.method.put-rollback-version}")
    void rollbackVersion(@PathVariable("productId") String productId);
}
