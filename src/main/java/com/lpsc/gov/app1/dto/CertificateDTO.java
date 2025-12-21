
package com.lpsc.gov.app1.dto;

import java.io.Serializable;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpsc.gov.app1.generics.GlobalVariables;
import com.lpsc.gov.app1.pojo.MCertificate;

import org.hibernate.Session;

public class CertificateDTO extends TransferDTO {

    public MCertificate mCertificate;

    public CertificateDTO() {
        super(GlobalVariables.CERTIFICATE_DTO);

    }

    private <T, ID extends Serializable> void saveIfNotExists(
            Session session,
            Class<T> entityClass,
            T entity,
            Function<T, ID> idExtractor,
            boolean update) {
        try {
            if (entity != null) {
                ID id = idExtractor.apply(entity);
                T existing = session.get(entityClass, id);
                if (existing == null) {
                    session.save(entity);
                } else {
                    if (update) {
                        session.merge(entity);
                    }
                }
                session.detach(existing);

            }
        } catch (Exception e) {
            throw e;
        }
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
        try {
            saveIfNotExists(session, MCertificate.class, mCertificate, MCertificate::getId, true);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

}
