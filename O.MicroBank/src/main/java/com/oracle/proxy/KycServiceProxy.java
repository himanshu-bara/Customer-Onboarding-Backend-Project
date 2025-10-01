package com.oracle.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.oracle.customer.dto.KycVerificationResponse;

@FeignClient(name = "kyc-service", contextId = "kycProxy", path = "/kycservice/api")
public interface KycServiceProxy {

    @GetMapping("/kyc/{customerId}")
    KycVerificationResponse getKycDetails(@PathVariable("customerId") String customerId);
}
