package com.accountingmoney.controller;

import com.accountingmoney.service.OperationService;
import com.accountingmoney.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
public class OperationController {
    private final Logger logger = LoggerFactory.getLogger(OperationController.class);

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String deposit(@RequestParam(value = "number") String number, @RequestParam(value = "amount") long amount) {
        ResultCode resultCode = operationService.deposit(number, BigDecimal.valueOf(amount));
        return resultCode.getCode();
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdraw(@RequestParam(value = "number") String number, @RequestParam(value = "amount") long amount) {
        ResultCode resultCode = operationService.withdraw(number, BigDecimal.valueOf(amount));
        return resultCode.getCode();
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public String transfer(@RequestParam(value = "from") String numberFrom,
                        @RequestParam(value = "to") String numberTo,
                        @RequestParam(value = "amount") long amount) {
        ResultCode resultCode = operationService.transfer(numberFrom, numberTo, BigDecimal.valueOf(amount));
        return resultCode.getCode();
    }

    @ExceptionHandler({Exception.class})
    public String handleError(HttpServletRequest req, Exception ex) {
        logger.error("Request: " + req.getRequestURL() + " reason " + ex);
        return ResultCode.ERROR.getCode();
    }
}
