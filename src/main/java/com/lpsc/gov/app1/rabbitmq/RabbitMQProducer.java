package com.lpsc.gov.app1.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpsc.gov.app1.generics.GlobalVariables;
import com.lpsc.gov.app1.generics.RPCPayload;
import com.lpsc.gov.app1.pojo.RPCCalls;
import com.lpsc.gov.app1.services.RPCServiceI;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("rabbitMQProducer")
public class RabbitMQProducer {

    @Autowired
    @Qualifier("rabbitMQChannel")
    private Channel rabbitMQChannel;

    @Autowired
    private RPCServiceI rpcService;

    public void sendRPCPayload(String serviceName, String methodName, Map<String, Object> params) {
        RPCPayload rpcPayload = new RPCPayload();
        rpcPayload.setServiceName(serviceName);
        rpcPayload.setMethodName(methodName);
        rpcPayload.setParams(params);

        RPCCalls rpcCall = rpcService.addNewCall(rpcPayload.getServiceName(),
                rpcPayload.getMethodName(), rpcPayload.getParams().toString());
        long rpcId = rpcCall.getRpcid();
        rpcPayload.setRequestId(rpcId);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String message = mapper.writeValueAsString(rpcPayload);
            Map<String, Object> headers = new HashMap<>();
            headers.put("messageType", GlobalVariables.RPCPayload);

            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().headers(headers).build();

            rabbitMQChannel.basicPublish("", RabbitMQConfig.OUTBOX_QUEUE, props,
                    message.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
