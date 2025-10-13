
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
import com.lpsc.gov.app1.generics.GlobalVariables;
import com.lpsc.gov.app1.generics.RPCPayload;
import com.lpsc.gov.app1.generics.RPCResult;
import com.lpsc.gov.app1.pojo.RPCCalls;
import com.lpsc.gov.app1.pojo.TestTable;
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
    private TestTableServiceI testtableService;

    @Autowired
    private RPCServiceI rpcService;


    @Autowired
    @Qualifier("rabbitMQChannel")
    private Channel rabbitMQChannel;

    private boolean executionResult(RPCPayload payload) {

        String serviceName = payload.getServiceName();
        String methodName = payload.getMethodName();
        Map<String, Object> params = payload.getParams();

        boolean result = false;
        ObjectMapper mapper = new ObjectMapper();

        switch (serviceName) {
            case "TestTableServiceI":
                switch (methodName) {
                    case "saveTable":
                        System.out.println(params.get("table").getClass().getName());
                        TestTable table = mapper.convertValue(params.get("table"), TestTable.class);
                        if (table != null) {
                            TestTable r = testtableService.saveTable(table);
                            if (r != null)
                                result = true;

                            RPCResult rpcResult = new RPCResult(payload.getRequestId(),
                                    payload.getRabbitmqid(), (result == true) ? 1 : 0);

                            try {
                                String message = mapper.writeValueAsString(rpcResult);
                                HashMap<String, Object> headers = new HashMap<>();
                                headers.put("messageType", GlobalVariables.RPCResult);
                                // AMQBasicProperties props =
                                // new AMQP.BasicProperties.Builder().headers(headers).build();

                                AMQP.BasicProperties props =
                                        new AMQP.BasicProperties.Builder().headers(headers).build();



                                rabbitMQChannel.basicPublish("", RabbitMQConfig.OUTBOX_QUEUE, props,
                                        message.getBytes());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        // table.setName(table.getName() + "-INSIDE CONSUMER ");
                        break;

                    default:
                        break;
                }
                break;


            default:
                break;
        }


        return result;
    }


    private boolean processAcknowledgement(RPCResult r) {
        boolean res = false;
        try {
            RPCCalls rpccall = rpcService.getRPCCallById(r.getRequestId());
            // Hibernate.initialize(rpccall);
            // System.out.println(null);
            // System.out.println(r.getRequestId() + " -- " + r.getResult());
            // Hibernate.initialize(rpccall.getResult());
            // rpccall.getResult();
            rpccall.setResult(r.getResult());
            // rpccall.setResultDate(new java.sql.Timestamp(System.currentTimeMillis()));
            rpcService.updateCall(rpccall);
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    @PostConstruct
    public void processMessages() {
        /*
         * DeliverCallback deliveryCallBackRequests = (consumerTag, delivery) -> { String message =
         * new String(delivery.getBody(), StandardCharsets.UTF_8);
         * System.out.println(" [x] Received Request to execute Procedure '" + message + "'");
         * 
         * ObjectMapper mapper = new ObjectMapper(); RPCPayload payload = mapper.readValue(message,
         * RPCPayload.class); boolean result = executionResult(payload);
         * 
         * System.out.
         * println("****************************\n Message was processed successfully \n **************************"
         * );
         * 
         * };
         * 
         * DeliverCallback deliveryCallBackResults = (consumerTag, delivery) -> { String message =
         * new String(delivery.getBody(), StandardCharsets.UTF_8);
         * System.out.println(" [x] Received Result message '" + message + "'");
         * 
         * ObjectMapper mapper = new ObjectMapper(); RPCResult resultPayload =
         * mapper.readValue(message, RPCResult.class); boolean result =
         * processAcknowledgement(resultPayload);
         * 
         * System.out.
         * println("****************************\n Results Callback  was processed successfully; Request = "
         * + resultPayload.getRequestId() + " Result = " + resultPayload.getResult() +
         * " \n **************************");
         * 
         * };
         */

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
            /*
             * rabbitMQChannel.basicConsume(RabbitMQConfig.CONSUMER_REQUEST_QUEUE, true,
             * deliveryCallBackRequests, consumerTag -> { });
             * rabbitMQChannel.basicConsume(RabbitMQConfig.CONSUMER_REPLY_QUEUE, true,
             * deliveryCallBackResults, consumerTag -> { });
             */
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
