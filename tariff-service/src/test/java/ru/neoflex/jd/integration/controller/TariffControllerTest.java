package ru.neoflex.jd.integration.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TariffControllerTest extends AbstractSpringContextTest {

    @BeforeEach
    void setUp() {
        tariffRepository.deleteAll();
    }

    @Test
    void createTariffSuccessfulTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.put("/product/update-tariff")
                        .willReturn(aResponse()
                                .withStatus(200))
        );

        mockMvc.perform(post("/tariff/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getTariffDto())))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTariffSuccessfulTest() throws Exception {
        tariffRepository.save(getTariff());
        mockMvc.perform(delete("/tariff/delete/3fa85f64-5717-4562-b3fc-2c963f66afa5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateTariffSuccessfulTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.put("/product/update-tariff")
                        .willReturn(aResponse()
                                .withStatus(200))
        );

        tariffRepository.save(getTariff());
        mockMvc.perform(put("/tariff/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getTariffDto())))
                .andExpect(status().isOk());
    }

    @Test
    void saveTariffRequestForProductServiceSuccessfulTest() throws Exception {
        mockMvc.perform(post("/tariff/integration/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getTariffDto())))
                .andExpect(status().isOk());
    }
}
