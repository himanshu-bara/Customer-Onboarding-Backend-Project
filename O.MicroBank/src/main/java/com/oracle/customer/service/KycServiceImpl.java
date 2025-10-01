package com.oracle.customer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.customer.dao.KycDAO;
import com.oracle.customer.dto.CustomerResponseDTO;
import com.oracle.customer.dto.KycDTO;
import com.oracle.customer.exception.CustomerNotFoundException;
import com.oracle.customer.exception.IdentityMismatchException;
import com.oracle.customer.exception.KycAlreadyExistsException;
import com.oracle.customer.exception.KycNotFoundException;
import com.oracle.customer.model.Kyc;
import com.oracle.customer.util.KycMapper;
import com.oracle.proxy.AccountServiceProxy;
import com.oracle.proxy.CustomerServiceProxy;

import feign.FeignException;

@Service
public class KycServiceImpl implements KycService {

	@Autowired
	private KycDAO kycDAO;
	

	
	@SuppressWarnings("unused")
	@Autowired
	private AccountServiceProxy accountServiceProxy;

	
	@Autowired
	private CustomerServiceProxy customerServiceProxy; // assuming this returns customer details

	@Override
	public KycDTO saveKyc(KycDTO kycDTO) {
	    CustomerResponseDTO customer;
	    try {
	        customer = customerServiceProxy.getCustomerById(kycDTO.getCustomerId());
	    } catch (FeignException.NotFound ex) {
	        throw new CustomerNotFoundException(kycDTO.getCustomerId());
	    }

	    if (!customer.getMaskedPan().endsWith(kycDTO.getPanNumber().substring(6))) {
	        throw new IdentityMismatchException("PAN number does not match the registered customer.");
	    }

	    if (!customer.getMaskedAadhaar().endsWith(kycDTO.getAadhaarNumber().substring(8))) {
	        throw new IdentityMismatchException("Aadhaar number does not match the registered customer.");
	    }

	 // Prevent re-submission
	    Kyc existing = kycDAO.getKycByCustomerId(kycDTO.getCustomerId());
	    if (existing != null) {
	        throw new KycAlreadyExistsException("KYC already submitted for customerId: " + kycDTO.getCustomerId());
	    }

	    // Detect file type from base64
	    String base64 = kycDTO.getPhotograph();
	    if (base64 != null && base64.startsWith("/9j/")) {
	        kycDTO.setDocumentType("image/jpeg");
	    } else if (base64 != null && base64.startsWith("iVBOR")) {
	        kycDTO.setDocumentType("image/png");
	    } else {
	        kycDTO.setDocumentType("unknown");
	    }

	    kycDTO.setDocumentUrl("uploaded-via-base64");

	    Kyc kyc = KycMapper.toEntity(kycDTO);
	    kyc.setKycStatus("PENDING");

	    return KycMapper.toDto(kycDAO.saveKyc(kyc));
	}



	@Override
	public KycDTO getKycByCustomerId(String customerId) {
		Kyc kyc = kycDAO.getKycByCustomerId(customerId);
		if (kyc == null) {
			throw new KycNotFoundException(customerId);
		}
		return KycMapper.toDto(kyc);
	}

	@Override
	public List<KycDTO> getAllKycs() {
		return kycDAO.getAllKycs().stream().map(KycMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public KycDTO updateKyc(String customerId, KycDTO kycDTO) {
		Kyc existing = kycDAO.getKycByCustomerId(customerId);
		if (existing == null) {
			throw new KycNotFoundException(customerId);
		}

		Kyc updated = KycMapper.toEntity(kycDTO);
		updated.setCustomerId(customerId);
		Kyc saved = kycDAO.updateKyc(updated);
		return KycMapper.toDto(saved);
	}

	@Override
	public void deleteKyc(String customerId) {
		Kyc existing = kycDAO.getKycByCustomerId(customerId);
		if (existing == null) {
			throw new KycNotFoundException(customerId);
		}
		kycDAO.deleteKyc(customerId);
	}


	@Override
	public void reviewKyc(String customerId, String status, String remark) {
	    Kyc kyc = kycDAO.getKycByCustomerId(customerId);
	    if (kyc == null) throw new KycNotFoundException(customerId);

	    kyc.setKycStatus(status.toUpperCase());
	    kyc.setAdminRemark(remark);
	    kycDAO.updateKyc(kyc);

	    @SuppressWarnings("unused")
		CustomerResponseDTO customer = customerServiceProxy.getCustomerById(customerId);

	    
//	    if ("REJECTED".equalsIgnoreCase(status)) {
//	        EmailRequest emailRequest = new EmailRequest(
//	            customer.getEmail(),
//	            "KYC Rejection Notice for Customer ID: " + customerId,
//	            "Dear Customer,\n\nYour KYC has been rejected for the following reason:\n\n" + remark +
//	            "\n\nPlease correct the issue and re-submit.\n\nThanks,\nBank Admin"
//	        );
//	        emailProducer.sendEmail(emailRequest);
//	    } else if ("VERIFIED".equalsIgnoreCase(status)) {
//	        try {
//	            // Call account microservice
//	            AccountRequestDTO accountRequest = new AccountRequestDTO(customerId);
//	            AccountResponseDTO response = accountServiceProxy.createAccount(accountRequest);
//
//	            // Send success email
//	            EmailRequest successEmail = new EmailRequest(
//	                customer.getEmail(),
//	                "Account Created Successfully",
//	                "Dear Customer,\n\nYour account has been successfully created.\n\nAccount Number: "
//	                + response.getAccountNumber() + "\n\nThank you for banking with us.\n\nRegards,\nBank Team"
//	            );
//	            emailProducer.sendEmail(successEmail);
//	        } catch (Exception e) {
//	            // Send failure email if account creation fails
//	            EmailRequest failureEmail = new EmailRequest(
//	                customer.getEmail(),
//	                "Account Creation Failed",
//	                "Dear Customer,\n\nWe verified your KYC but encountered an error while creating your account.\n\n"
//	                + "Error: " + e.getMessage() + "\n\nPlease contact support.\n\nRegards,\nBank Team"
//	            );
//	            emailProducer.sendEmail(failureEmail);
//	        }
//	    }
	}

	}
