
package com.lpsc.gov.app1;

import java.nio.charset.StandardCharsets;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class InterMediary {

    public static void main(String[] args) {

        final String INTER_INBOX = "INTER_INBOX";
        final String INTRA_INBOX = "INTRA_INBOX";
        final String INTRA_OUTBOX = "INTRA_OUTBOX";
        final String INTER_OUTBOX = "INTER_OUTBOX";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(INTER_INBOX, false, false, false, null);
            channel.queueDeclare(INTRA_INBOX, false, false, false, null);
            channel.queueDeclare(INTRA_OUTBOX, false, false, false, null);
            channel.queueDeclare(INTER_OUTBOX, false, false, false, null);


            DeliverCallback internetCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("Message received from Intranet " + message);
                channel.basicPublish("", INTER_INBOX, delivery.getProperties(), delivery.getBody());
            };

            DeliverCallback intranetCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("Message received from Internet " + message);
                channel.basicPublish("", INTRA_INBOX, delivery.getProperties(), delivery.getBody());
            };

            System.out.println("Started processing for sending messages");

            channel.basicConsume(INTRA_OUTBOX, true, internetCallback, consumerTag -> {
            });

            channel.basicConsume(INTER_OUTBOX, true, intranetCallback, consumerTag -> {
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
