package com.testtask.moneytransfer;

import com.testtask.moneytransfer.repository.MoneytransferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class MoneytransferApplicationTests {
    protected static final String RESOURCE_URL = "/v1/account";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MoneytransferRepository moneytransferRepository;

    @Test
    public void testAnonymous() throws Exception {
        mockMvc.perform(get("/fakeEndpoint"))
                .andExpect(status().isNotFound());

    }

    @Test
    void addToNonExistingAccount() throws Exception {
        mockMvc.perform(
                        put(RESOURCE_URL + "/3/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"value\":\"1\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("100500: account not found")));
    }

    @Test
    void addNonToValidAccount() throws Exception {
        mockMvc.perform(
                        put(RESOURCE_URL + "/0/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"value\":1}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", is("100501: account id is not valid")));
    }

    @Test
    void addNonValidValue() throws Exception {
        mockMvc.perform(
                        put(RESOURCE_URL + "/1/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"value\":0}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", is("100501: value is not valid")));
    }

    @Test
    void deductTooMuch() throws Exception {
        mockMvc.perform(
                        put(RESOURCE_URL + "/1/deduct")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"value\":\"900\"}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", is("100502: negative balance")));

    }

    @Test
    void transferTooMuch() throws Exception {
        mockMvc.perform(
                        put(RESOURCE_URL + "/1/transfer/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"value\":\"900\"}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", is("100502: negative balance")));

    }

    @Test
    void addSuccess() throws Exception {
        mockMvc.perform(
                        put(RESOURCE_URL + "/1/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"value\":\"1\"}"))
                .andExpect(status().isNoContent());

    }
    @Test
    void deductSuccess() throws Exception {
        mockMvc.perform(
                        put(RESOURCE_URL + "/2/deduct")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"value\":\"1\"}"))
                .andExpect(status().isNoContent());

    }

    @Test
    void transferSuccess() throws Exception {
        mockMvc.perform(
                        put(RESOURCE_URL + "/1/transfer/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"value\":\"1\"}"))
                .andExpect(status().isNoContent());

    }

    @Test
    void contextLoads() {
    }

}
