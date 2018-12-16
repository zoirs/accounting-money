package com.accountingmoney.service;

import com.accountingmoney.model.Account;

public interface AccountService {
    String create(String name);
    Account find(String number);
    void update(Account account);
}
