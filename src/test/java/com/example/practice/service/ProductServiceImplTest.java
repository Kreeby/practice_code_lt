package com.example.practice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.practice.service.impl.ProductServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ProductServiceImpl.class)
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setup() throws IOException {
        Path file = tempDir.resolve("product.csv");
        Files.write(file, Stream.of(
                "product_id,product_name",
                "1,Treasury Bills Domestic",
                "2,Corporate Bonds Domestic"
        ).collect(Collectors.toList()));

        System.setProperty("product.data.filepath", file.toString());

    }

    @Test
    void whenProductIdExists_thenReturnsProductName() {
        assertEquals("Treasury Bills Domestic", productService.getProductName(1));
        assertEquals("Corporate Bonds Domestic", productService.getProductName(2));
    }

    @Test
    void whenProductIdDoesNotExist_thenReturnsMissingProductName() {
        assertEquals("Missing Product Name", productService.getProductName(999));
    }
}
