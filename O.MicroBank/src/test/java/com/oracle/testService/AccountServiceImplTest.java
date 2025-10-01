package com.oracle.testService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.oracle.customer.dao.AccountDAO;
import com.oracle.customer.dto.AccountDTO;
import com.oracle.customer.dto.KycVerificationResponse;
import com.oracle.customer.exception.AccountNotFoundException;
import com.oracle.customer.exception.IdentityMismatchException;
import com.oracle.customer.exception.KycNotVerifiedException;
import com.oracle.customer.model.Account;
import com.oracle.customer.service.AccountServiceImpl;
import com.oracle.kafka.PublishEmailNotification;
import com.oracle.proxy.KycServiceProxy;

public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private KycServiceProxy kycServiceProxy;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Mock
    private PublishEmailNotification notification;


    private AccountDTO createValidAccountDTO() {
        AccountDTO dto = new AccountDTO();
        dto.setCustomerId("CUST123");
        dto.setPanNumber("ABCDE1234F");
        dto.setAadhaarNumber("123456789012");
        dto.setAccountType("Savings");
        return dto;
    }

    private KycVerificationResponse createValidKycResponse() {
        KycVerificationResponse kyc = new KycVerificationResponse();
        kyc.setKycStatus("VERIFIED");
        kyc.setPanNumber("ABCDE1234F");
        kyc.setAadhaarNumber("123456789012");
        return kyc;
    }

    @Test
    void testCreateAccount_Success() {
        AccountDTO dto = createValidAccountDTO();
        KycVerificationResponse kyc = createValidKycResponse();
        when(kycServiceProxy.getKycDetails("CUST123")).thenReturn(kyc);

        Account savedAccount = new Account();
        savedAccount.setAccountId("AC12345678");
        savedAccount.setCustomerId("CUST123");
        savedAccount.setAccountType("Savings");
        savedAccount.setAccountStatus("ACTIVE");

        when(accountDAO.saveAccount(any(Account.class))).thenReturn(savedAccount);

        AccountDTO result = accountService.createAccount(dto);
        assertEquals("CUST123", result.getCustomerId());
        assertEquals("Savings", result.getAccountType());
        assertEquals("ACTIVE", result.getAccountStatus());
    }

    @Test
    void testCreateAccount_KycNotVerified() {
        AccountDTO dto = createValidAccountDTO();
        KycVerificationResponse kyc = createValidKycResponse();
        kyc.setKycStatus("PENDING");

        when(kycServiceProxy.getKycDetails("CUST123")).thenReturn(kyc);

        assertThrows(KycNotVerifiedException.class, () -> accountService.createAccount(dto));
    }

    @Test
    void testCreateAccount_IdentityMismatch() {
        AccountDTO dto = createValidAccountDTO();
        KycVerificationResponse kyc = createValidKycResponse();
        kyc.setPanNumber("WRONG1234F");  // Mismatch

        when(kycServiceProxy.getKycDetails("CUST123")).thenReturn(kyc);

        assertThrows(IdentityMismatchException.class, () -> accountService.createAccount(dto));
    }

    @Test
    void testGetAccountByCustomerId_Success() {
        Account account = new Account();
        account.setAccountId("AC00000001");
        account.setCustomerId("CUST999");
        account.setAccountType("Current");
        account.setAccountStatus("ACTIVE");

        when(accountDAO.findByCustomerId("CUST999")).thenReturn(account);

        AccountDTO dto = accountService.getAccountByCustomerId("CUST999");

        assertEquals("CUST999", dto.getCustomerId());
        assertEquals("Current", dto.getAccountType());
    }

    @Test
    void testGetAccountByCustomerId_NotFound() {
        when(accountDAO.findByCustomerId("CUST404")).thenReturn(null);

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountByCustomerId("CUST404"));
    }
}
