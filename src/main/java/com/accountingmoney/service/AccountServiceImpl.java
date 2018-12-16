package com.accountingmoney.service;

import com.accountingmoney.model.Account;
import com.accountingmoney.repository.AccountRepository;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public String create(String name) {
        Account account = new Account(name, BigDecimal.ZERO);
        accountRepository.save(account);
        return account.getNumber();
    }

    @Override
    public Account find(String number) {
        return accountRepository.findByNumber(number).orElse(null);
    }

    @Override
    public void update(Account account) {
        accountRepository.save(account);
    }
}
