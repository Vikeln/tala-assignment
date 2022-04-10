package com.tala.account;

import com.tala.account.controllers.AccountController;
import com.tala.account.domain.models.CustomerAccountBalance;
import com.tala.account.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AccountController.class})
class AccountApplicationTests {

    @Value("${default.account.number}")
    private String defaultAccountNumber;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService service;

    @Test
    void getBalance() throws Exception {
        CustomerAccountBalance response = new CustomerAccountBalance(defaultAccountNumber, 0);
        ResponseEntity responseEntity = ResponseEntity.ok(response);

        when(service.accountBalanceResponse(defaultAccountNumber)).thenReturn(responseEntity);

        this.mockMvc.perform((get("/api/account/balance")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber", is(response.getAccountNumber())))
                .andExpect(jsonPath("$.balance", is(response.getBalance())));
    }

}
