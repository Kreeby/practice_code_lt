package com.example.practice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import com.example.practice.service.impl.TradeServiceImpl;
import com.example.practice.model.Trade;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class TradeServiceImplTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private TradeServiceImpl tradeService;

    private MockMultipartFile file;

    @BeforeEach
    void setUp() {
        String csvData = "date,product_id,currency,price\n20200101,1,EUR,10.0\n20200102,2,USD,20.0\ninvalid,3,EUR,30.0";
        file = new MockMultipartFile("file", "trades.csv", "text/csv", csvData.getBytes());
    }

    @Test
    void whenFileHasValidAndInvalidTrades_thenOnlyValidTradesAreProcessed() throws IOException {
        when(productService.getProductName(1)).thenReturn("Treasury Bills Domestic");
        when(productService.getProductName(2)).thenReturn("Corporate Bonds Domestic");

        List<Trade> result = tradeService.processAndEnrichTrades(file, productService);

        assertEquals(2, result.size()); // Assuming there are 2 valid entries in your CSV
        assertEquals("Treasury Bills Domestic", result.get(0).getProductName());
        assertEquals("Corporate Bonds Domestic", result.get(1).getProductName());
    }


    @Test
    void whenDateIsInvalid_thenTradeIsNotIncluded() throws IOException {
        List<Trade> result = tradeService.processAndEnrichTrades(file, productService);

        result.forEach(trade -> assertNotEquals("invalid", trade.getDate()));
        assertEquals(2, result.size());
    }
}
