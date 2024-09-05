package ru.neoflex.jd.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TariffGatewayControllerTest extends AbstractSpringContextTest {

    @Test
    void createTariffSuccessfulTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.post("/tariff/create")
                        .willReturn(aResponse()
                                .withStatus(200))
        );

        mockMvc.perform(post("/tarifficator/tariff/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getTariffDto())))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTariffSuccessfulTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.delete("/tariff/delete/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(mapper.writeValueAsString(getTariffDto()))
                                .withStatus(200))
        );

        mockMvc.perform(delete("/tarifficator/tariff/delete/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateTariffSuccessfulTest() throws Exception {
        wireMockExtension.stubFor(
                WireMock.put("/tariff/update/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(mapper.writeValueAsString(getTariffDto()))
                                .withStatus(200))
        );

        mockMvc.perform(put("/tarifficator/tariff/update/3fa85f64-5717-4562-b3fc-2c963f66afa6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getTariffDto())))
                .andExpect(status().isOk());
    }


}
