package com.oracle.customer.controller;


import com.oracle.customer.dto.AccountDTO;
import com.oracle.customer.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account-api")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @Operation(
        summary = "Create a new bank account",
        description = "Creates an account for a customer only if their KYC is VERIFIED. Automatically generates a mock account number."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account created successfully"),
        @ApiResponse(responseCode = "400", description = "KYC not verified or invalid request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    
    @PostMapping("/account")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO dto) {
        return ResponseEntity.ok(accountService.createAccount(dto));
    }

    @Operation(
            summary = "Get account by customer ID",
            description = "Fetches the account details linked to the specified customer ID."
        )
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account found"),
            @ApiResponse(responseCode = "404", description = "No account found for the given customer ID")
        })
    @GetMapping("/account/customer")
    public ResponseEntity<AccountDTO> getByCustomerId(@RequestParam("customerId") String customerId) {
        return ResponseEntity.ok(accountService.getAccountByCustomerId(customerId));
    }
}
