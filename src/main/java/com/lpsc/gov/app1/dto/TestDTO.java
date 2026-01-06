package com.lpsc.gov.app1.dto;

import com.lpsc.gov.app1.generics.GlobalVariables;

import org.hibernate.Session;

public class TestDTO extends TransferDTO {

    String message;

    public TestDTO() {
        super(GlobalVariables.TEST_DTO);
    }

    @Override
    public String convertToMessage() {
        return message;
    }

    @Override
    public String getDBMessage() {
        return message;
    }

    @Override
    public String testmethod() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'testmethod'");
    }

    @Override
    public boolean saveData(Session session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveData'");
    }

}
