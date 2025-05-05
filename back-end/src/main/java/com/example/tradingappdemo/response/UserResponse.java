package com.example.tradingappdemo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private LocalDateTime responseTime;
    private ResponseResult responseResult;
    private String username;
    private String email;
}
