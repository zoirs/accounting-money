package com.accountingmoney;

import com.accountingmoney.model.Account;
import com.accountingmoney.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    AccountService accountService;

    @Test
    public void createAccountTest() throws Exception {
        this.mockMvc
                .perform(get("/createAccount").param("name", "Вася"))
                .andExpect(status().isOk()).andDo(mvcResult -> {
            String number = mvcResult.getResponse().getContentAsString();
            assertNotNull(number);
            Account account = accountService.find(number);
            assertNotNull(account);
            assertThat(account.getOwner(), is(account.getOwner()));
        });
    }
}
