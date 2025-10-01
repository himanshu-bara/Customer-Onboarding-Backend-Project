package com.oracle.customer.dao;

import java.util.List;

import com.oracle.customer.model.Kyc;

public interface KycDAO {

    Kyc saveKyc(Kyc kyc);

    Kyc getKycByCustomerId(String customerId);

    List<Kyc> getAllKycs();

    Kyc updateKyc(Kyc kyc);

    void deleteKyc(String customerId);
}
