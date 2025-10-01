package com.oracle.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.oracle.customer.dto.AccountRequestDTO;
import com.oracle.customer.dto.AccountResponseDTO;

@FeignClient(name = "account-service", contextId = "accountProxy", url = "http://localhost:8082") // update port if different
public interface AccountServiceProxy {
	@PostMapping("/api/accounts")
	AccountResponseDTO createAccount(@RequestBody AccountRequestDTO accountDTO);
}
