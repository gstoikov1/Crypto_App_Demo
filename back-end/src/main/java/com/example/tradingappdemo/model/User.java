package com.example.tradingappdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private BigDecimal funds;

}
