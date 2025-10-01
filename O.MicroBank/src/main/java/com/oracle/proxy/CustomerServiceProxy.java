package com.oracle.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.oracle.customer.dto.CustomerResponseDTO;


@FeignClient(name = "customer-service",  contextId = "customerProxy", path = "/customerservice/api")
public interface CustomerServiceProxy {

    // Fix: use /customers/{id} instead of /customer/{id}
    @GetMapping("/customers/{id}")
    CustomerResponseDTO getCustomerById(@PathVariable("id") String id);
}
