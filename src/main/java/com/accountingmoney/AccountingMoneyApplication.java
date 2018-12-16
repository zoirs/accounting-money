package com.accountingmoney;

import com.accountingmoney.repository.AccountRepository;
import com.accountingmoney.service.AccountService;
import com.accountingmoney.service.AccountServiceImpl;
import com.accountingmoney.service.OperationService;
import com.accountingmoney.service.OperationServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountingMoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountingMoneyApplication.class, args);
	}

	@Bean
	public AccountService accountService(AccountRepository accountRepository){
		return new AccountServiceImpl(accountRepository);
	}

	@Bean
	public OperationService greetingService(AccountService accountService) {
		return new OperationServiceImpl(accountService);
	}

}

