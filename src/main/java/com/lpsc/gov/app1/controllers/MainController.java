package com.lpsc.gov.app1.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpsc.gov.app1.dto.FileTransferDTO;
import com.lpsc.gov.app1.encryption.AESHelper;
import com.lpsc.gov.app1.generics.GlobalVariables;
import com.lpsc.gov.app1.generics.RPCPayload;
import com.lpsc.gov.app1.pojo.RPCCalls;
import com.lpsc.gov.app1.pojo.TestTable;
import com.lpsc.gov.app1.rabbitmq.RabbitMQConfig;
import com.lpsc.gov.app1.rabbitmq.RabbitMQProducer;
import com.lpsc.gov.app1.services.RPCServiceI;
import com.lpsc.gov.app1.services.TestTableServiceI;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

@RestController
public class MainController {

    @Autowired
    private TestTableServiceI testTableService;

    @Autowired
    private RPCServiceI rpcService;

    @Autowired
    @Qualifier("rabbitMQChannel")
    private Channel rabbitMQChannel;

    @Autowired
    private Environment env;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/allrecords")
    public List<TestTable> allRecords() {
        System.out.println(env.getProperty("server.networktype"));
        return testTableService.getTables();
    }

    @GetMapping("/populaterandom")
    public String populateRandom() {
        String randomString = getSaltString();
        TestTable table = new TestTable();
        table.setName(randomString);
        table.setCode("LB12345");

        testTableService.saveTable(table);

        // Map<String, Object> params = new HashMap<>();
        // Hibernate.initialize(table);
        // params.put("table", table);
        // rabbitMQProducer.sendRPCPayload("TestTableServiceI", "saveTable", params);

        return "Saved successfully";
    }

    @GetMapping("/updateRandom")
    public String updateRandom() {

        TestTable tableEntry = testTableService.getTestById(1);
        // System.out.println("Random Entry name = " + tableEntry.getName());
        tableEntry.setName(getSaltString());
        testTableService.saveTable(tableEntry);

        // RPCCalls rpcall = rpcService.addNewCall("TestTableServiceI", "saveTable", ))
        Map<String, Object> params = new HashMap<>();
        Hibernate.initialize(tableEntry);
        params.put("table", tableEntry);

        rabbitMQProducer.sendRPCPayload("TestTableServiceI", null);
        return "Updated Successfully";

    }

    @GetMapping("/testmq")
    public String testMQ() {
        try {
            rabbitMQChannel.basicPublish("", RabbitMQConfig.OUTBOX_QUEUE, null,
                    "Sample message from rabbitMQ".getBytes());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Pushed task to queue";
    }

    @GetMapping("/transferfile")
    public String transferFile() {

        FileTransferDTO fileTransferDTO = new FileTransferDTO();
        byte[] array = null;

        try {
            array = Files.readAllBytes(Paths.get("C:\\Users\\Test\\dddd\\2512.24601.pdf"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        fileTransferDTO.setFileBytes(array);
        fileTransferDTO.setFileName("2512.24601.pdf");
        String messagePayload = fileTransferDTO.convertToMessage();

        rabbitMQProducer.sendRPCPayload(fileTransferDTO.getDTOType(), fileTransferDTO);

        return "FileTransferDTO succeeded";

    }

    @GetMapping("/encryptiontest")
    public String testEncryption() {

        String messagetoEncrypt = " Mobile phone";
        String key = "ZXSId27TQqtAP9jgZ5aP5Q==";
        // String key =
        // "d2193f45f70dcaac1876f88a205acbeb710c6163893eba79ac0ea89964c473ec";
        String encryptedMessage = "";
        try {
            encryptedMessage = AESHelper.encrypt(messagetoEncrypt, key);

            System.out.print("_____________________");
            System.out.println(encryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String decryptedmessage = AESHelper.decrypt(encryptedMessage, key);

            System.out.println("***********************");
            System.out.println(decryptedmessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FileTransferDTO succeeded";

    }

}
