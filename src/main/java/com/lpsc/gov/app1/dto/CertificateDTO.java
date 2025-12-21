
package com.lpsc.gov.app1.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpsc.gov.app1.pojo.MCertificate;

import org.hibernate.Session;

public class CertificateDTO extends TransferDTO {

    public MCertificate mCertificate;

    public CertificateDTO() {
        super("CertificateDTO");

    }

    @Override
    public String convertToMessage() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{}";
        try {
            jsonString = mapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonString;

    }

    @Override
    public String testmethod() {
        throw new UnsupportedOperationException("Unimplemented method 'testmethod'");
    }

    @Override
    public boolean saveData(Session session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveData'");
    }

}
