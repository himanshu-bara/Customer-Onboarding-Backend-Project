package com.oracle.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Account Data Transfer Object")
public class AccountDTO {
    @Schema(description = "Unique account ID", example = "ACF1D2A3B4")
    private String accountId;

    @Schema(description = "Customer ID", example = "CUST123")
    private String customerId;

    @Schema(description = "Type of account", example = "Savings")
    private String accountType;

    @Schema(description = "Status of the account", example = "ACTIVE")
    private String accountStatus;
    
    @Schema(description = "PAN Number", example = "ABCDE1234F")
    private String panNumber;

    @Schema(description = "Aadhaar Number", example = "123456789012")
    private String aadhaarNumber;
    
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getAccountStatus() { return accountStatus; }
    public void setAccountStatus(String accountStatus) { this.accountStatus = accountStatus; }
    
    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }

    public String getAadhaarNumber() { return aadhaarNumber; }
    public void setAadhaarNumber(String aadhaarNumber) { this.aadhaarNumber = aadhaarNumber; }
}
