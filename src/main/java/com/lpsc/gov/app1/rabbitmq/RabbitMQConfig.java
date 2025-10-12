package com.lpsc.gov.app1.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Configuration
public class RabbitMQConfig {
    
    public static String PRODUCER_QUEUE = "PRODUCER_QUEUE";
    public static String PRODUCER_REPLY_QUEUE = "PRODUCER_QUEUE";
    public static String CONSUMER_REQUEST_QUEUE = "PRODUCER_QUEUE";
    public static String CONSUMER_REPLY_QUEUE = "PRODUCER_QUEUE";

    private Connection rabbitConnection;
    private Channel messagingChannel;

    @Autowired
    private Environment env;

    @PostConstruct
    public void setUpQueues() {
        String networkType = env.getProperty("server.networktype");
        if(networkType.equals("INTRANET")) {
            PRODUCER_QUEUE = "INTRA_OUTBOX";
            CONSUMER_REQUEST_QUEUE = "INTER_OUTBOX";
            CONSUMER_REPLY_QUEUE = "INTRA_INBOX";
            PRODUCER_REPLY_QUEUE = "INTER_INBOX";
        }
        else if(networkType.equals("INTERNET")) {
            PRODUCER_QUEUE = "INTER_OUTBOX";
            CONSUMER_REQUEST_QUEUE = "INTRA_OUTBOX";
            CONSUMER_REPLY_QUEUE = "INTER_INBOX";
            PRODUCER_REPLY_QUEUE = "INTRA_INBOX";
        }

    }

    @Bean(name = "rabbitMQChannel")
    public Channel getRabbitMQConnection(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        try {
            rabbitConnection = connectionFactory.newConnection();
            messagingChannel = rabbitConnection.createChannel();
            messagingChannel.queueDeclare(PRODUCER_QUEUE, false, false, false, null);
            messagingChannel.queueDeclare(CONSUMER_REQUEST_QUEUE, false, false, false, null);
            messagingChannel.queueDeclare(CONSUMER_REPLY_QUEUE, false, false, false, null);
            //messagingChannel.queueDeclare(, false, false, false, null)
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messagingChannel;
    }

    @PreDestroy
    public void configDestroy() {
        
        try {
            if(messagingChannel != null)
                messagingChannel.close();
            if(rabbitConnection != null)
                rabbitConnection.close();
        } catch (Exception e) {
            
            e.printStackTrace();
        } 
        
    }


}
