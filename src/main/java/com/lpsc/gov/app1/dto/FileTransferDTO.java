package com.lpsc.gov.app1.dto;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpsc.gov.app1.generics.GlobalVariables;

import org.hibernate.Session;

public class FileTransferDTO extends TransferDTO {

    private String fileName;
    private byte[] fileBytes;

    public FileTransferDTO() {
        super(GlobalVariables.FILETRANSFER_DTO);

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
    public String getDBMessage() {
        return this.fileName;
    }

    @Override
    public String testmethod() {
        throw new UnsupportedOperationException("Unimplemented method 'testmethod'");
    }

    @Override
    public boolean saveData(Session session) {
        // TODO Auto-generated method stub
        //
        String path = Paths.get(GlobalVariables.FILE_LOCATION, this.fileName).toString();
        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(this.fileBytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
