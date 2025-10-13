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

    public static String INBOX_QUEUE = "INBOX";
    public static String OUTBOX_QUEUE = "OUTBOX";

    private Connection rabbitConnection;
    private Channel messagingChannel;

    @Autowired
    private Environment env;

    @PostConstruct
    public void setUpQueues() {
        String networkType = env.getProperty("server.networktype");
        if (networkType.equals("INTRANET")) {
            INBOX_QUEUE = "INTRA_INBOX";
            OUTBOX_QUEUE = "INTRA_OUTBOX";
        } else if (networkType.equals("INTERNET")) {
            INBOX_QUEUE = "INTER_INBOX";
            OUTBOX_QUEUE = "INTER_OUTBOX";
        }
    }

    @Bean(name = "rabbitMQChannel")
    public Channel getRabbitMQConnection() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        try {
            rabbitConnection = connectionFactory.newConnection();
            messagingChannel = rabbitConnection.createChannel();

            messagingChannel.queueDeclare(INBOX_QUEUE, false, false, false, null);
            messagingChannel.queueDeclare(OUTBOX_QUEUE, false, false, false, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messagingChannel;
    }

    @PreDestroy
    public void configDestroy() {

        try {
            if (messagingChannel != null)
                messagingChannel.close();
            if (rabbitConnection != null)
                rabbitConnection.close();
        } catch (Exception e) {

            e.printStackTrace();
        }

    }


}
