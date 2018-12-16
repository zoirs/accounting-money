package com.accountingmoney;

import com.accountingmoney.model.Account;
import com.accountingmoney.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Test
    public void createAccountTest() {
        String number = accountService.create("Вася");
        Account account = accountService.find(number);
        assertNotNull(account);
        assertThat(account.getOwner(), is("Вася"));
    }
}
