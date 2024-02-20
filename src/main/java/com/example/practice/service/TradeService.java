package com.example.practice.service;

import com.example.practice.model.Trade;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TradeService {
    List<Trade> processAndEnrichTrades(MultipartFile file, ProductService productService) throws Exception;
}
