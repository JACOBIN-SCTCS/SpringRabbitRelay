package com.lpsc.gov.app1.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpsc.gov.app1.generics.GlobalVariables;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DTOHelper")
public class DTOHelper {

    @Autowired
    private SessionFactory sessionFactory;

    public static String getType(String message) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(message);
            return root.get("dtotype").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static TransferDTO deserializeMessage(String message) {

        String type = getType(message);
        TransferDTO transferDTO = null;
        ObjectMapper mapper = new ObjectMapper();

        try {

            switch (type) {
                case GlobalVariables.LIBRARY_DTO:
                    transferDTO = mapper.readValue(message, LibraryDTO.class);
                    break;
                case GlobalVariables.CERTIFICATE_DTO:
                    transferDTO = mapper.readValue(message, CertificateDTO.class);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return transferDTO;
    }

    public boolean applyState(String message) {

        TransferDTO dto = deserializeMessage(message);
        boolean result = false;
        Session session = null;

        Transaction transaction = null;
        try {

            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            result = dto.saveData(session);
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }

        return result;

    }
}
