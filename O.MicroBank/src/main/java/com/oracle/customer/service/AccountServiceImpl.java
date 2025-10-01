package com.oracle.customer.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.customer.dao.AccountDAO;
import com.oracle.customer.dto.AccountDTO;
import com.oracle.customer.dto.KycVerificationResponse;
import com.oracle.customer.exception.AccountNotFoundException;
import com.oracle.customer.exception.IdentityMismatchException;
import com.oracle.customer.exception.KycNotVerifiedException;
import com.oracle.customer.model.Account;
import com.oracle.customer.util.AccountMapper;
import com.oracle.proxy.KycServiceProxy;
import com.oracle.kafka.PublishEmailNotification;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private PublishEmailNotification notification;
	
    @Autowired
    private AccountDAO accountDAO;
    
    @Autowired
    private KycServiceProxy kycServiceProxy;

    @Override
    public AccountDTO createAccount(AccountDTO dto) {

    	KycVerificationResponse kyc = kycServiceProxy.getKycDetails(dto.getCustomerId());

    	if (!"VERIFIED".equalsIgnoreCase(kyc.getKycStatus())) {
    	    throw new KycNotVerifiedException(dto.getCustomerId());
    	}

    	if (!dto.getPanNumber().equalsIgnoreCase(kyc.getPanNumber()) ||
    	    !dto.getAadhaarNumber().equalsIgnoreCase(kyc.getAadhaarNumber())) {
    	    throw new IdentityMismatchException("PAN or Aadhaar does not match the verified KYC data.");
    	}


        //Proceed with account creation
        Account account = AccountMapper.toEntity(dto);
        account.setAccountId(generateMockAccountNumber());
        account.setAccountStatus("ACTIVE");
        Account saved = accountDAO.saveAccount(account);
        notification.sendAccountEmailNotification(account);
        return AccountMapper.toDTO(saved);
    }


    @Override
    public AccountDTO getAccountByCustomerId(String customerId) {
        Account account = accountDAO.findByCustomerId(customerId);
        if (account == null) {
            throw new AccountNotFoundException(customerId);
        }
        return AccountMapper.toDTO(account);
    }

    private String generateMockAccountNumber() {
        return "AC" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10).toUpperCase();
    }
}
