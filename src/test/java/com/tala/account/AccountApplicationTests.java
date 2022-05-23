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

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({AccountController.class}) // use @WebMvcTest annotation for testing the web layer of your Spring Boot application. Will only instantiate AccountController in your web layer.
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

        this.mockMvc.perform((get("/api/account/balance?accountNumber={accountNumber}", defaultAccountNumber)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber", is(response.getAccountNumber())))
                .andExpect(jsonPath("$.balance", is(response.getBalance())));

//        Verify that the accountBalanceResponse() method of the AccountService interface is called only once.
        verify(service, times(1)).accountBalanceResponse(defaultAccountNumber);
//        Ensure that no other methods of our mock object are called during the test.
        verifyNoMoreInteractions(service);
    }

    @Test
    void withdraw() throws Exception {
        Status status = Response.SUCCESS.status();

        ResponseEntity responseEntity = ResponseEntity.ok(status);

        when(service.transact(TransactionTypes.WITHDRAW, defaultAccountNumber, 500)).thenReturn(responseEntity);

        this.mockMvc.perform((post("/api/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":500,\"accountNumber\":\"" + defaultAccountNumber + "\"}")
        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code", equalTo(status.getCode())));
//        Verify that the transact() method of the AccountService interface is called only once.
        verify(service, times(1)).transact(TransactionTypes.WITHDRAW, defaultAccountNumber, 500);
//        Ensure that no other methods of our mock object are called during the test.
        verifyNoMoreInteractions(service);
    }

    @Test
    void withdrawTooMuch() throws Exception {
        Status status = Response.AMOUNT_EXCEED_MAX_WITHDRAWAL_PER_DAY.status();

        ResponseEntity responseEntity = ResponseEntity.ok(status);
        when(service.transact(TransactionTypes.WITHDRAW, defaultAccountNumber, 50000)).thenReturn(responseEntity);

        this.mockMvc.perform((post("/api/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":50000,\"accountNumber\":\"" + defaultAccountNumber + "\"}")
        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code", equalTo(status.getCode())));
//        Verify that the transact() method of the AccountService interface is called only once.
        verify(service, times(1)).transact(TransactionTypes.WITHDRAW, defaultAccountNumber, 50000);
//        Ensure that no other methods of our mock object are called during the test.
        verifyNoMoreInteractions(service);
    }

    @Test
    void withdrawTooMuchPerTransaction() throws Exception {
        Status status = Response.EXCEED_MAX_WITHDRAWAL_PER_TRANSACTION.status();
        ResponseEntity responseEntity = ResponseEntity.ok(status);

        when(service.transact(TransactionTypes.WITHDRAW, defaultAccountNumber, 60000)).thenReturn(responseEntity);

        this.mockMvc.perform((post("/api/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":60000,\"accountNumber\":\"" + defaultAccountNumber + "\"}")
        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code", equalTo(status.getCode())));
//        Verify that the transact() method of the AccountService interface is called only once.
        verify(service, times(1)).transact(TransactionTypes.WITHDRAW, defaultAccountNumber, 60000);
//        Ensure that no other methods of our mock object are called during the test.
        verifyNoMoreInteractions(service);
    }

    @Test
    void withdrawTooMuchPerDay() throws Exception {
        Status status = Response.EXCEED_MAX_WITHDRAWALS_FOR_TODAY.status();
        ResponseEntity responseEntity = ResponseEntity.ok(status);

        when(service.transact(TransactionTypes.WITHDRAW, defaultAccountNumber, 50000)).thenReturn(responseEntity);

        this.mockMvc.perform((post("/api/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":50000,\"accountNumber\":\"" + defaultAccountNumber + "\"}")
        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code", equalTo(status.getCode())));
//        Verify that the transact() method of the AccountService interface is called only once.
        verify(service, times(1)).transact(TransactionTypes.WITHDRAW, defaultAccountNumber, 50000);
//        Ensure that no other methods of our mock object are called during the test.
        verifyNoMoreInteractions(service);
    }

    @Test
    void insufficientBalance() throws Exception {
        Status status = Response.INSUFFICIENT_BALANCE.status();
        ResponseEntity responseEntity = ResponseEntity.ok(status);

        when(service.transact(TransactionTypes.WITHDRAW, defaultAccountNumber, 50000)).thenReturn(responseEntity);

        this.mockMvc.perform((post("/api/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":50000,\"accountNumber\":\"" + defaultAccountNumber + "\"}")
        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code", equalTo(status.getCode())));
//        Verify that the transact() method of the AccountService interface is called only once.
        verify(service, times(1)).transact(TransactionTypes.WITHDRAW, defaultAccountNumber, 50000);
//        Ensure that no other methods of our mock object are called during the test.
        verifyNoMoreInteractions(service);
    }

    @Test
    void withdrawFromNonExistingAccount() throws Exception {
        Status status = Response.ACCOUNT_NOT_FOUND.status();
        ResponseEntity responseEntity = ResponseEntity.ok(status);

        when(service.transact(TransactionTypes.WITHDRAW, defaultAccountNumber, 30000)).thenReturn(responseEntity);

        this.mockMvc.perform((post("/api/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":30000,\"accountNumber\":\"" + defaultAccountNumber + "\"}")
        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code", equalTo(status.getCode())));

//        Verify that the transact() method of the AccountService interface is called only once.
        verify(service, times(1)).transact(TransactionTypes.WITHDRAW, defaultAccountNumber, 30000);
//        Ensure that no other methods of our mock object are called during the test.
        verifyNoMoreInteractions(service);
    }

    @Test
    void deposit() throws Exception {
        Status status = Response.SUCCESS.status();
        ResponseEntity responseEntity = ResponseEntity.ok(status);

        when(service.transact(TransactionTypes.DEPOSIT, defaultAccountNumber, 30)).thenReturn(responseEntity);

        this.mockMvc.perform((post("/api/account/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":30,\"accountNumber\":\"" + defaultAccountNumber + "\"}")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code", equalTo(status.getCode())));

//        Verify that the transact() method of the AccountService interface is called only once.
        verify(service, times(1)).transact(TransactionTypes.DEPOSIT, defaultAccountNumber, 30);
//        Ensure that no other methods of our mock object are called during the test.
        verifyNoMoreInteractions(service);
    }

    @Test
    void depositTooMuch() throws Exception {
        Status status = Response.EXCEED_MAX_DEPOSIT_AMOUNT_PER_TRANSACTION.status();
        ResponseEntity responseEntity = ResponseEntity.ok(status);

        when(service.transact(TransactionTypes.DEPOSIT, defaultAccountNumber, 50000)).thenReturn(responseEntity);

        this.mockMvc.perform((post("/api/account/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":50000,\"accountNumber\":\"" + defaultAccountNumber + "\"}")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code", equalTo(status.getCode())));

//        Verify that the transact() method of the AccountService interface is called only once.
        verify(service, times(1)).transact(TransactionTypes.DEPOSIT, defaultAccountNumber, 50000);
//        Ensure that no other methods of our mock object are called during the test.
        verifyNoMoreInteractions(service);
    }

    @Test
    void depositTooMuchPerTransaction() throws Exception {
        Status status = Response.EXCEED_MAX_DEPOSIT_AMOUNT_PER_TRANSACTION.status();
        ResponseEntity responseEntity = ResponseEntity.ok(status);

        when(service.transact(TransactionTypes.DEPOSIT, defaultAccountNumber, 40000)).thenReturn(responseEntity);

        this.mockMvc.perform((post("/api/account/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":40000,\"accountNumber\":\"" + defaultAccountNumber + "\"}")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code", equalTo(status.getCode())));

//        Verify that the transact() method of the AccountService interface is called only once.
        verify(service, times(1)).transact(TransactionTypes.DEPOSIT, defaultAccountNumber, 40000);
//        Ensure that no other methods of our mock object are called during the test.
        verifyNoMoreInteractions(service);
    }

    @Test
    void depositTooMuchPerDay() throws Exception {
        Status status = Response.EXCEED_MAX_DEPOSITS.status();
        ResponseEntity responseEntity = ResponseEntity.ok(status);

        when(service.transact(TransactionTypes.DEPOSIT, defaultAccountNumber, 150000)).thenReturn(responseEntity);

        this.mockMvc.perform((post("/api/account/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":150000,\"accountNumber\":\"" + defaultAccountNumber + "\"}")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code", equalTo(status.getCode())));

//        Verify that the transact() method of the AccountService interface is called only once.
        verify(service, times(1)).transact(TransactionTypes.DEPOSIT, defaultAccountNumber, 150000);
//        Ensure that no other methods of our mock object are called during the test.
        verifyNoMoreInteractions(service);
    }

    @Test
    void transactionValidations() throws Exception {

        String validationErrorMessage = "Validation error.";

        this.mockMvc.perform((post("/api/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":0,\"accountNumber\":\"" + "\"}")
        ))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo(validationErrorMessage)))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("amount", "accountNumber")))

                .andExpect(jsonPath("$.errors[*].message", containsInAnyOrder("Minimum allowed transaction amount is 10", "must not be empty")));

        verifyNoInteractions(service);
    }

    @Test
    void nullTransactionValidations() throws Exception {

        this.mockMvc.perform((post("/api/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
        ))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }
}
