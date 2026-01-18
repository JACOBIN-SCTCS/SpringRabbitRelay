package com.lpsc.gov.app1.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.lpsc.gov.app1.generics.GlobalVariables;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(
                VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        try {

            switch (type) {
                case GlobalVariables.LIBRARY_DTO:
                    transferDTO = mapper.readValue(message, LibraryDTO.class);
                    break;
                case GlobalVariables.FILETRANSFER_DTO:
                    transferDTO = mapper.readValue(message, FileTransferDTO.class);
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
        try {
            session = sessionFactory.openSession();
            result = dto.saveData(session);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }

        return result;

    }
}
