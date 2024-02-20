package com.example.practice.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.example.practice.model.Trade;
import com.example.practice.service.ProductService;
import com.example.practice.service.TradeService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @MockBean
    private ProductService productService;

    @MockBean
    private TradeService tradeService;

    @Autowired
    private MockMvc mockMvc;

    private MockMultipartFile file;

    @BeforeEach
    public void setup() {
        // Setup common objects used by multiple test methods
        file = new MockMultipartFile(
                "file",
                "filename.csv",
                "text/csv",
                "test,data\n123,ABC".getBytes());
    }

    @Test
    public void testSubmitTradesWithProductNames_Success() throws Exception {
        Trade trade = new Trade();
        List<Trade> trades = List.of(trade);
        given(tradeService.processAndEnrichTrades(file, productService)).willReturn(trades);

        mockMvc.perform(multipart("/api/v1/trades/with-product-names")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Test
    public void testSubmitTradesWithProductNames_Failure() throws Exception {
        given(tradeService.processAndEnrichTrades(file, productService))
                .willThrow(new RuntimeException("Test Exception"));

        mockMvc.perform(multipart("/api/v1/trades/with-product-names")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(
                        "Failed to enrich trades with product names")));
    }
}
