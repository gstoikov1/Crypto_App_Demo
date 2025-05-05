package com.example.tradingappdemo.response;

import com.example.tradingappdemo.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTransactionResponse {
    private ResponseResult responseResult;
    private List<Transaction> transactions;
}
