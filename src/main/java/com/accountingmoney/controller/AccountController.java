package com.accountingmoney.controller;

import com.accountingmoney.service.AccountService;
import com.accountingmoney.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/createAccount")
    public String createAccount(@RequestParam(value = "name") String name) {
        return accountService.create(name);
    }

    @ExceptionHandler({Exception.class})
    public String handleError(HttpServletRequest req, Exception ex) {
        logger.error("Request: " + req.getRequestURL() + " reason " + ex);
        return ResultCode.ERROR.getCode();
    }
}
