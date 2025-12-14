
package com.lpsc.gov.app1.rabbitmq;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpsc.gov.app1.dto.DTOHelper;
import com.lpsc.gov.app1.dto.TransferDTO;
import com.lpsc.gov.app1.generics.GlobalVariables;
import com.lpsc.gov.app1.generics.RPCPayload;
import com.lpsc.gov.app1.generics.RPCResult;
import com.lpsc.gov.app1.pojo.Library;
import com.lpsc.gov.app1.pojo.RPCCalls;
import com.lpsc.gov.app1.pojo.TestTable;
import com.lpsc.gov.app1.services.LibraryService;
import com.lpsc.gov.app1.services.LibraryServiceI;
import com.lpsc.gov.app1.services.RPCServiceI;
import com.lpsc.gov.app1.services.TestTableServiceI;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.impl.AMQBasicProperties;
import jakarta.annotation.PostConstruct;

@Component("rabbitMQConsumer")
public class RabbitMQConsumer {

    @Autowired
    private RPCServiceI rpcService;

    @Autowired
    private DTOHelper dtoHelper;

    @Autowired
    @Qualifier("rabbitMQChannel")
    private Channel rabbitMQChannel;

    private void sendRPCResult(long requestId, long rabbitMqId, boolean result) {
        RPCResult rpcResult = new RPCResult(requestId, rabbitMqId, (result == true) ? 1 : 0);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String message = mapper.writeValueAsString(rpcResult);
            HashMap<String, Object> headers = new HashMap<>();
            headers.put("messageType", GlobalVariables.RPCResult);
            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().headers(headers).build();

            rabbitMQChannel.basicPublish("", RabbitMQConfig.OUTBOX_QUEUE, props,
                    message.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean executionResult(RPCPayload payload) {

        boolean result = false;

        result = dtoHelper.applyState(payload.getPayload());
        sendRPCResult(payload.getRequestId(), payload.getRabbitmqid(), result);

        return result;
    }

    private boolean processAcknowledgement(RPCResult r) {
        boolean res = false;
        try {
            RPCCalls rpccall = rpcService.getRPCCallById(r.getRequestId());
            rpccall.setResult(r.getResult());
            rpcService.updateCall(rpccall);
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @PostConstruct
    public void processMessages() {

        DeliverCallback deliveryCallBack = (consumerTag, delivery) -> {
            Map<String, Object> headers = delivery.getProperties().getHeaders();
            String messageType = headers != null && headers.get("messageType") != null
                    ? headers.get("messageType").toString()
                    : null;

            ObjectMapper mapper = new ObjectMapper();
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            if (messageType != null) {
                if (messageType.equals(GlobalVariables.RPCPayload)) {
                    RPCPayload payload = mapper.readValue(message, RPCPayload.class);
                    executionResult(payload);
                } else if (messageType.equals(GlobalVariables.RPCResult)) {
                    RPCResult payload = mapper.readValue(message, RPCResult.class);
                    processAcknowledgement(payload);
                }
            }

        };

        try {

            rabbitMQChannel.basicConsume(RabbitMQConfig.INBOX_QUEUE, true, deliveryCallBack,
                    consumerTag -> {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
