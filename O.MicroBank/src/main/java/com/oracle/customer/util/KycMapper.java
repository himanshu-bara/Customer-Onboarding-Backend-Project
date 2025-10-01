package com.oracle.customer.util;

import java.util.Base64;

import com.oracle.customer.dto.KycDTO;
import com.oracle.customer.model.Kyc;

public class KycMapper {

    public static KycDTO toDto(Kyc kyc) {
        if (kyc == null) return null;

        KycDTO dto = new KycDTO();
        dto.setCustomerId(kyc.getCustomerId());
        dto.setPanNumber(kyc.getPanNumber());
        dto.setAadhaarNumber(kyc.getAadhaarNumber());
        
        if (kyc.getPhotograph() != null) {
            dto.setPhotograph(Base64.getEncoder().encodeToString(kyc.getPhotograph()));
        } else {
            dto.setPhotograph(null);
        }

        dto.setDocumentType(kyc.getDocumentType());
        dto.setDocumentUrl(kyc.getDocumentUrl());

        dto.setKycStatus(kyc.getKycStatus());
        dto.setAdminRemark(kyc.getAdminRemark());

        dto.setMaskedPan(maskPan(kyc.getPanNumber()));
        dto.setMaskedAadhaar(maskAadhaar(kyc.getAadhaarNumber()));
        return dto;
    }

    public static Kyc toEntity(KycDTO dto) {
        if (dto == null) return null;

        Kyc kyc = new Kyc();
        kyc.setCustomerId(dto.getCustomerId());
        kyc.setPanNumber(dto.getPanNumber());
        kyc.setAadhaarNumber(dto.getAadhaarNumber());
        

        if (dto.getPhotograph() != null && !dto.getPhotograph().isEmpty()) {
            kyc.setPhotograph(Base64.getDecoder().decode(dto.getPhotograph()));
        } else {
            kyc.setPhotograph(null);
        }

        kyc.setDocumentType(dto.getDocumentType());
        kyc.setDocumentUrl(dto.getDocumentUrl());
        kyc.setKycStatus(dto.getKycStatus()); // Optional
        kyc.setAdminRemark(dto.getAdminRemark());
        return kyc;
    }

    private static String maskPan(String pan) {
        return (pan != null && pan.length() >= 10) ? "XXXXXX" + pan.substring(6) : pan;
    }

    private static String maskAadhaar(String aadhaar) {
        return (aadhaar != null && aadhaar.length() >= 12) ? "XXXX-XXXX-" + aadhaar.substring(8) : aadhaar;
    }
}
