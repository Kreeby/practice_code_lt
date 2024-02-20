package com.example.practice.service.impl;

import com.example.practice.model.Trade;
import com.example.practice.service.ProductService;
import com.example.practice.service.TradeService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service for processing and enriching trade data.
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TradeServiceImpl implements TradeService {

    /**
     * Processes and enriches trades from a given file.
     *
     * @param file            the multipart file containing trade data
     * @param productService the service to retrieve product names
     * @return a list of enriched trades
     * @throws IOException if an error occurs during file processing
     */
    @Override
    public List<Trade> processAndEnrichTrades(MultipartFile file, ProductService productService) throws IOException {
        List<Trade> trades = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            reader.lines().skip(1).forEach(line -> {
                String[] parts = line.split(",");
                if (isValidDate(parts[0])) {
                    Trade trade = new Trade(
                            parts[0],
                            Integer.parseInt(parts[1]),
                            parts[2],
                            Double.parseDouble(parts[3]),
                            productService.getProductName(Integer.parseInt(parts[1])));
                    trades.add(trade);
                } else {
                    log.error("Invalid date format for trade: {}", line);
                }
            });
        }
        log.info("Processed and enriched {} trades.", trades.size());
        return trades;
    }

    /**
     * Validates the format of a given date string.
     *
     * @param date the date string
     * @return true if the date is valid, false otherwise
     */
    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            log.error("Date parsing failed for: {}", date, e);
            return false;
        }
    }
}
