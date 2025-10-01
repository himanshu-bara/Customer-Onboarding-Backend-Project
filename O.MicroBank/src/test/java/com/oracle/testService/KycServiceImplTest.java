package com.oracle.testService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.oracle.customer.dao.KycDAO;
import com.oracle.customer.dto.CustomerResponseDTO;
import com.oracle.customer.dto.KycDTO;
import com.oracle.customer.model.Kyc;
import com.oracle.customer.service.KycServiceImpl;
import com.oracle.proxy.AccountServiceProxy;
import com.oracle.proxy.CustomerServiceProxy;

public class KycServiceImplTest {

    @InjectMocks
    private KycServiceImpl kycService;

    @Mock
    private KycDAO kycDAO;

    @Mock
    private AccountServiceProxy accountServiceProxy;

    @Mock
    private CustomerServiceProxy customerServiceProxy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private KycDTO createMockKycDTO() {
        KycDTO dto = new KycDTO();
        dto.setCustomerId("cust001");
        dto.setPanNumber("ABCDE1234F");
        dto.setAadhaarNumber("123456789012");
        dto.setPhotograph("/9j/sampleBase64");
        return dto;
    }

    private CustomerResponseDTO createMockCustomer() {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setMaskedPan("XXXXX1234F");
        dto.setMaskedAadhaar("XXXXXXXX9012");
        dto.setEmail("test@example.com");
        return dto;
    }

    @Test
    void testSaveKyc() {
        KycDTO dto = createMockKycDTO();
        CustomerResponseDTO customer = createMockCustomer();

        when(customerServiceProxy.getCustomerById("cust001")).thenReturn(customer);
        when(kycDAO.getKycByCustomerId("cust001")).thenReturn(null);
        when(kycDAO.saveKyc(any(Kyc.class))).thenAnswer(i -> i.getArgument(0));

        KycDTO saved = kycService.saveKyc(dto);

        assertEquals("cust001", saved.getCustomerId());
        assertEquals("PENDING", saved.getKycStatus());
        assertEquals("image/jpeg", saved.getDocumentType());
    }

    @Test
    void testGetKycByCustomerId() {
        Kyc kyc = new Kyc();
        kyc.setCustomerId("cust001");
        kyc.setKycStatus("PENDING");

        when(kycDAO.getKycByCustomerId("cust001")).thenReturn(kyc);

        KycDTO dto = kycService.getKycByCustomerId("cust001");

        assertEquals("cust001", dto.getCustomerId());
        assertEquals("PENDING", dto.getKycStatus());
    }

    @Test
    void testGetAllKycs() {
        Kyc k1 = new Kyc();
        k1.setCustomerId("cust001");
        k1.setKycStatus("PENDING");

        when(kycDAO.getAllKycs()).thenReturn(List.of(k1));

        List<KycDTO> list = kycService.getAllKycs();

        assertEquals(1, list.size());
        assertEquals("cust001", list.get(0).getCustomerId());
    }

    @Test
    void testUpdateKyc() {
        KycDTO dto = createMockKycDTO();
        Kyc existing = new Kyc();
        existing.setCustomerId("cust001");

        when(kycDAO.getKycByCustomerId("cust001")).thenReturn(existing);
        when(kycDAO.updateKyc(any(Kyc.class))).thenAnswer(i -> i.getArgument(0));

        KycDTO updated = kycService.updateKyc("cust001", dto);

        assertEquals("cust001", updated.getCustomerId());
    }

    @Test
    void testDeleteKyc() {
        Kyc existing = new Kyc();
        existing.setCustomerId("cust001");

        when(kycDAO.getKycByCustomerId("cust001")).thenReturn(existing);

        kycService.deleteKyc("cust001");

        verify(kycDAO, times(1)).deleteKyc("cust001");
    }

    @Test
    void testReviewKyc() {
        Kyc kyc = new Kyc();
        kyc.setCustomerId("cust001");

        CustomerResponseDTO customer = createMockCustomer();

        when(kycDAO.getKycByCustomerId("cust001")).thenReturn(kyc);
        when(customerServiceProxy.getCustomerById("cust001")).thenReturn(customer);

        kycService.reviewKyc("cust001", "VERIFIED", "KYC passed");

        assertEquals("VERIFIED", kyc.getKycStatus());
        assertEquals("KYC passed", kyc.getAdminRemark());
    }
}