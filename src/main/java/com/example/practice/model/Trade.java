package com.example.practice.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Trade {
    String date;
    Integer productId;
    String currency;
    Double price;
    String productName;
}
