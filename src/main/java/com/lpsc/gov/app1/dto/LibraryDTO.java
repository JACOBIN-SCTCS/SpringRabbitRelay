package com.lpsc.gov.app1.dto;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpsc.gov.app1.pojo.Books;

public class LibraryDTO extends TransferDTO {

    public int id;
    public String libraryName;
    public int active;
    public String migrationState;
    public List<Books> books;

    public LibraryDTO() {
        super("Library");
        ;
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

}
