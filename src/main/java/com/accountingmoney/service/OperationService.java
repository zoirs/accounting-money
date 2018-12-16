package com.accountingmoney.service;

import com.accountingmoney.utils.ResultCode;

import java.math.BigDecimal;

public interface OperationService {
    ResultCode withdraw(String id, BigDecimal money);

    ResultCode deposit(String id, BigDecimal money);

    ResultCode transfer(String from, String to, BigDecimal money);
}
