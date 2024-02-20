package com.example.practice.controller;

import com.example.practice.model.Trade;
import com.example.practice.service.ProductService;
import com.example.practice.service.TradeService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for handling requests related to trade data.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TradeController {

    ProductService productService;
    TradeService tradeService;

    /**
     * Endpoint to submit and enrich trades with product names.
     *
     * @param file the file containing trades to be enriched
     * @return a response entity containing the enriched trades or an error message
     */
    @PostMapping("/trades/with-product-names")
    public ResponseEntity<?> submitTradesWithProductNames(@RequestParam("file") MultipartFile file) {
        try {
            List<Trade> trades = tradeService.processAndEnrichTrades(file, productService);
            log.info("Successfully enriched {} trades.", trades.size());
            return new ResponseEntity<>(trades, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to enrich trades with product names: ", e);
            throw new RuntimeException("Failed to enrich trades with product names", e);
        }
    }
}
