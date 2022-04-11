package com.tala.account;

import com.tala.account.controllers.AccountController;
import com.tala.account.domain.TransactionTypes;
import com.tala.account.domain.models.CustomerAccountBalance;
import com.tala.account.domain.models.Response;
import com.tala.account.domain.models.Status;
import com.tala.account.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        this.mockMvc.perform((get("/api/account/balance?accountNumber={accountNumber}",defaultAccountNumber)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber", is(response.getAccountNumber())))
                .andExpect(jsonPath("$.balance", is(response.getBalance())));
    }

    @Test
    void withdraw() throws Exception {
        Status status = Response.SUCCESS.status();
        ResponseEntity responseEntity = ResponseEntity.ok(status);

        when(service.transact(TransactionTypes.WITHDRAW,defaultAccountNumber,500,null)).thenReturn(responseEntity);

        this.mockMvc.perform((post("/api/account/{accountNumber}/withdraw",defaultAccountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":500,\"accountNumber\":\"" +defaultAccountNumber+ "\"}")
        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status.code", equalTo(0)));
    }

    @Test
    void deposit() throws Exception {
        Status status = Response.SUCCESS.status();
        ResponseEntity responseEntity = ResponseEntity.ok(status);

        when(service.transact(TransactionTypes.DEPOSIT,defaultAccountNumber,500,null)).thenReturn(responseEntity);

        this.mockMvc.perform((post("/api/account/{accountNumber}/deposit",defaultAccountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":500,\"accountNumber\":\"" +defaultAccountNumber+ "\"}")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status.code", equalTo(0)));
    }

}
