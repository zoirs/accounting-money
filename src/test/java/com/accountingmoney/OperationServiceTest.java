package com.accountingmoney;

import com.accountingmoney.model.Account;
import com.accountingmoney.service.AccountService;
import com.accountingmoney.service.OperationService;
import com.accountingmoney.utils.ResultCode;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationServiceTest {

    @Autowired
    AccountService accountService;
    @Autowired
    OperationService operationService;

    private String accountNumber_1;
    private String accountNumber_2;

    @Before
    public void before(){
        accountNumber_1 = accountService.create("Вася");
        accountNumber_2 = accountService.create("Илья");
    }

    @Test
    public void depositErrorAccountTest() {
        ResultCode code = operationService.deposit("НесуществующийНомер", BigDecimal.valueOf(1_000));
        assertThat(code, is(ResultCode.ACCOUNT_NOT_FOUND));
    }

    @Test
    public void depositSuccessTest() {
        ResultCode code = operationService.deposit(accountNumber_1, BigDecimal.valueOf(1_000));
        assertThat(code, is(ResultCode.SUCCESS));
        assertMoney(accountNumber_1, 1000);
    }

    @Test
    public void withdrawErrorAccountTest() {
        ResultCode code = operationService.withdraw("НесуществующийНомер", BigDecimal.valueOf(1_000));
        assertThat(code, is(ResultCode.ACCOUNT_NOT_FOUND));
    }

    @Test
    public void withdrawNoMoneyTest() {
        ResultCode code = operationService.withdraw(accountNumber_1, BigDecimal.valueOf(1_000));
        assertThat(code, is(ResultCode.NO_MONEY));
    }

    @Test
    public void withdrawSuccessTest() {
        operationService.deposit(accountNumber_1, BigDecimal.valueOf(1_000));
        ResultCode code = operationService.withdraw(accountNumber_1, BigDecimal.valueOf(1_000));
        assertThat(code, is(ResultCode.SUCCESS));
        assertMoney(accountNumber_1, 0);
    }

    @Test
    public void transferSuccessTest() {
        operationService.deposit(accountNumber_1, BigDecimal.valueOf(1_000));
        ResultCode code = operationService.transfer(accountNumber_1, accountNumber_2, BigDecimal.valueOf(1_000));
        assertThat(code, is(ResultCode.SUCCESS));
        assertMoney(accountNumber_1, 0);
        assertMoney(accountNumber_2, 1000);
    }
    @Test
    public void transferNoMoneyTest() {
        ResultCode code = operationService.transfer(accountNumber_1, accountNumber_2, BigDecimal.valueOf(1_000));
        assertThat(code, is(ResultCode.NO_MONEY));
        assertMoney(accountNumber_1, 0);
        assertMoney(accountNumber_2, 0);
    }

    private void assertMoney(String number, long money) {
        Account account = accountService.find(number);
        assertNotNull(account);
        assertThat(account.getAmount(),  Matchers.comparesEqualTo(BigDecimal.valueOf(money)));
    }
}
