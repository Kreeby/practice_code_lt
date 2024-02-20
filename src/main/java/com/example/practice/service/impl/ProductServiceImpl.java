package com.example.practice.service.impl;

import com.example.practice.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Service for managing product data.
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ProductServiceImpl implements ProductService {
    final Map<Integer, String> productMap = new ConcurrentHashMap<>();

    @Value("${product.data.filepath:product.csv}")
    String productDataFilePath;

    /**
     * Loads product data from a CSV file into memory on application startup.
     */
    @PostConstruct
    public void loadProductData() {
        try (Stream<String> stream = Files.lines(Paths.get(productDataFilePath))) {
            stream.skip(1).forEach(line -> {
                String[] parts = line.split(",");
                productMap.put(Integer.parseInt(parts[0]), parts[1]);
            });
            log.info("Loaded product data successfully.");
        } catch (IOException e) {
            log.error("Failed to load product data: ", e);
            throw new RuntimeException("Failed to load product data: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves the product name for a given product ID.
     *
     * @param productId the product ID
     * @return the product name, or "Missing Product Name" if not found
     */
    @Override
    public String getProductName(Integer productId) {
        return productMap.getOrDefault(productId, "Missing Product Name");
    }
}
