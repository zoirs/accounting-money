/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.accountingmoney;

import com.accountingmoney.model.Account;
import com.accountingmoney.service.AccountService;
import com.accountingmoney.service.OperationService;
import com.accountingmoney.utils.ResultCode;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OperationControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    AccountService accountService;
    @Autowired
    OperationService operationService;

    @Test
    public void depositTest() throws Exception {
        String number = accountService.create("Вася");
        this.mockMvc
                .perform(post("/deposit")
                        .param("number", number)
                        .param("amount", "100"))
                .andExpect(status().isOk()).andDo(mvcResult -> {
            assertResult(mvcResult, ResultCode.SUCCESS);
            assertMoney(number, 100);
        });
    }

    @Test
    public void withdrawNoMoneyTest() throws Exception {
        String number = accountService.create("Вася");
        this.mockMvc
                .perform(post("/withdraw")
                        .param("number", number)
                        .param("amount", "100"))
                .andExpect(status().isOk()).andDo(mvcResult -> {
            assertResult(mvcResult, ResultCode.NO_MONEY);
            assertMoney(number, 0);
        });
    }

    @Test
    public void transferMoneyTest() throws Exception {
        String numberFrom = accountService.create("Вася");
        String numberTo = accountService.create("Илья");
        operationService.deposit(numberFrom, BigDecimal.valueOf(1000));

        this.mockMvc
                .perform(post("/transfer")
                        .param("from", numberFrom)
                        .param("to", numberTo)
                        .param("amount", "500"))
                .andExpect(status().isOk()).andDo(mvcResult -> {
            assertResult(mvcResult, ResultCode.SUCCESS);
            assertMoney(numberFrom, 500);
            assertMoney(numberTo, 500);
        });
    }

    private void assertResult(MvcResult mvcResult, ResultCode resultCode) throws UnsupportedEncodingException {
        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        assertThat(result, is(resultCode.getCode()));
    }

    private void assertMoney(String number, long money) {
        Account account = accountService.find(number);
        assertNotNull(account);
        assertThat(account.getAmount(),  Matchers.comparesEqualTo(BigDecimal.valueOf(money)));
    }
}
