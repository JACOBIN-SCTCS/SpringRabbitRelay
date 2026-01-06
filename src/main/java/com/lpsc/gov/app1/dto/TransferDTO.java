package com.lpsc.gov.app1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.Session;

public abstract class TransferDTO {

    @JsonProperty("dtotype")
    String dtoType;

    TransferDTO(String type) {
        this.dtoType = type;
    }

    public String getDTOType() {
        return this.dtoType;
    }

    public abstract String convertToMessage();

    public abstract String getDBMessage();

    public abstract String testmethod();

    public abstract boolean saveData(Session session);
}
