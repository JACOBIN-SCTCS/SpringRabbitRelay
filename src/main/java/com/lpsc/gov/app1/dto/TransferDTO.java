package com.lpsc.gov.app1.dto;

public abstract class TransferDTO {

    String dtoType;

    TransferDTO(String type) {
        this.dtoType = type;
    }

    public String getDTOType() {
        return this.dtoType;
    }

    public abstract String convertToMessage();

}
