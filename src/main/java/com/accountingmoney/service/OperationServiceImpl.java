package com.accountingmoney.service;

import com.accountingmoney.model.Account;
import com.accountingmoney.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class OperationServiceImpl implements OperationService {

    private final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);

    private final AccountService accountService;

    public OperationServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResultCode withdraw(String number, BigDecimal money) {
        Account account = accountService.find(number);
        if (account == null) {
            logger.info("Account {} not found", number);
            return ResultCode.ACCOUNT_NOT_FOUND;
        }
        if (account.isOnAccount(money)) {
            logger.info("Account {} have not money", number);
            return ResultCode.NO_MONEY;
        }
        account.withdraw(money);
        accountService.update(account);
        logger.info("Withdraw from {}, success", number);
        return ResultCode.SUCCESS;
    }

    @Override
    public ResultCode deposit(String number, BigDecimal money) {
        Account account = accountService.find(number);
        if (account == null) {
            logger.info("Account {} not found", number);
            return ResultCode.ACCOUNT_NOT_FOUND;
        }
        account.deposit(money);
        accountService.update(account);
        logger.info("Deposit to {}, success", number);
        return ResultCode.SUCCESS;
    }

    @Transactional
    @Override
    public ResultCode transfer(String numberFrom, String numberTo, BigDecimal money) {
        Account accountFrom = accountService.find(numberFrom);
        if (accountFrom == null) {
            logger.info("Account {} not found", numberFrom);
            return ResultCode.ACCOUNT_NOT_FOUND;
        }
        Account accountTo = accountService.find(numberTo);
        if (accountTo == null) {
            logger.info("Account {} not found", numberFrom);
            return ResultCode.ACCOUNT_NOT_FOUND;
        }

        synchronized (accountFrom.getId() > accountTo.getId() ? accountFrom : accountTo) {
            synchronized (accountFrom.getId() > accountTo.getId() ? accountTo : accountFrom) {
                if (accountFrom.isOnAccount(money)) {
                    logger.info("Account {} have not money", accountFrom.getNumber());
                    return ResultCode.NO_MONEY;
                }
                accountFrom.withdraw(money);
                accountTo.deposit(money);

                accountService.update(accountTo);
                accountService.update(accountFrom);

                logger.info("Deposit from {} to {}, success", accountFrom.getNumber(), accountTo.getNumber());
                return ResultCode.SUCCESS;
            }
        }
    }

}
