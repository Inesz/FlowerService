package com.example.controller;

import com.example.flower.model.Flower;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class FlowerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /flowers -> 200")
    @SneakyThrows
    void findAllFlowers200() {
        String endpoint = "/flowers";

        mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("GET /flowers -> 3 out")
    @SneakyThrows
    void checkInsertedFlowers3() {
        String endpoint = "/flowers";
        var returnedListSize = 6;

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        String flowers = resultActions.andReturn().getResponse().getContentAsString();

        List<Flower> flowersFromJson = objectMapper.readValue(flowers, new TypeReference<>() {
        });

        assertAll("flowers 1,2,3",
                () -> assertEquals(returnedListSize, flowersFromJson.size()),
                () -> assertEquals("fuchsia", flowersFromJson.get(0).getName()),
                () -> assertEquals("geranium", flowersFromJson.get(1).getName()),
                () -> assertEquals("mallow", flowersFromJson.get(2).getName())
        );
    }
}