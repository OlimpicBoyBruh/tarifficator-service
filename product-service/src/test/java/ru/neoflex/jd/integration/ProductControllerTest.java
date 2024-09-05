package ru.neoflex.jd.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.neoflex.jd.entity.Product;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends AbstractSpringContextTest {

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void createProductSuccessfulTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.post("/tariff/integration/save")
                        .willReturn(aResponse()
                                .withStatus(200))
        );
        mockMvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getProductDto())))
                .andExpect(status().isOk());
    }

    @Test
    void createProductNullObjectRequestTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.post("/tariff/integration/save")
                        .willReturn(aResponse()
                                .withStatus(200))
        );
        mockMvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteProductSuccessfulTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.delete("/tariff/delete/3fa85f64-5717-4562-b3fc-2c963f66afa5")
                        .willReturn(aResponse()
                                .withStatus(200))
        );

        productRepository.save(getProduct());
        mockMvc.perform(delete("/product/delete/3fa85f64-5717-4562-b3fc-2c963f66afa5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getActualPreviousProductTest() throws Exception {
        productRepository.save(getProduct());
        mockMvc.perform(get("/product/3fa85f64-5717-4562-b3fc-2c963f66afa5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getRevisionVersionSuccessfulTest() throws Exception {
        Product product = getProduct();
        product.setTariff(null);
        productRepository.save(product);
        product.setTariff(getTariff());
        productRepository.save(product);

        mockMvc.perform(get("/product/previous-version/all/3fa85f64-5717-4562-b3fc-2c963f66afa5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void rollbackVersionSuccessfulTest() throws Exception {
        Product product = getProduct();
        product.setTariff(null);
        productRepository.save(product);
        product.setTariff(getTariff());
        productRepository.save(product);

        mockMvc.perform(put("/product/update/rollback-version/3fa85f64-5717-4562-b3fc-2c963f66afa5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getPreviousVersionForPeriodSuccessfulTest() throws Exception {
        Product product = getProduct();
        product.setTariff(null);
        productRepository.save(product);
        product.setTariff(getTariff());
        productRepository.save(product);

        mockMvc.perform(get("/product/previous-version")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productId", "3fa85f64-5717-4562-b3fc-2c963f66afa5")
                        .param("period", "2025-12-12"))
                .andExpect(status().isOk());
    }


}
