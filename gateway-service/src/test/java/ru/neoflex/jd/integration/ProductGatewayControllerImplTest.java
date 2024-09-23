package ru.neoflex.jd.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductGatewayControllerImplTest extends AbstractSpringContextTest {
    @Test
    void createProductSuccessfulTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.post("/product/create")
                        .willReturn(aResponse()
                                .withStatus(200))
        );

        mockMvc.perform(post("/tarifficator/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getProductDto())))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProductSuccessfulTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.delete("/product/delete/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(mapper.writeValueAsString(getProductDto()))
                                .withStatus(200))
        );

        mockMvc.perform(delete("/tarifficator/product/delete/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getActualProductSuccessfulTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.get("/product/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(mapper.writeValueAsString(getProductDto()))
                                .withStatus(200))
        );

        mockMvc.perform(get("/tarifficator/product/actual/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getProductVersionsSuccessfulTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.get("/product/previous-version/all/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(mapper.writeValueAsString(List.of(getProductAuditDto(), getProductAuditDto())))
                                .withStatus(200))
        );

        mockMvc.perform(get("/tarifficator/product/versions/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getPreviousVersionSuccessfulTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.get("/product/previous-version?productId=3fa85f64-5717-4562-b3fc-2c963f66afa6&period=1/1/25")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)

                                .withBody(mapper.writeValueAsString(List.of(getProductAuditDto(), getProductAuditDto())))
                                .withStatus(200))
        );

        mockMvc.perform(get("/tarifficator/product/previous-version")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productId", "3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .param("period", "2025-01-01")
                )
                .andExpect(status().isOk());
    }

    @Test
    void rollbackVersionSuccessfulTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.put("/product/update/rollback-version/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withStatus(200))
        );

        mockMvc.perform(put("/tarifficator/product/rollback-version/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
