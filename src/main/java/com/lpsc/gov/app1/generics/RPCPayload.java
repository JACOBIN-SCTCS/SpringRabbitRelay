package com.lpsc.gov.app1.generics;

import java.util.Map;

public class RPCPayload {

    private long requestId;

    private String callType;

    private long rabbitmqid;

    private String payload;

    public RPCPayload() {
        ;
    }

    public RPCPayload(long requestId, String callType, long rabbitmqid, String payload) {
        this.requestId = requestId;
        this.callType = callType;
        this.rabbitmqid = rabbitmqid;
        this.payload = payload;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public long getRabbitmqid() {
        return rabbitmqid;
    }

    public void setRabbitmqid(long rabbitmqid) {
        this.rabbitmqid = rabbitmqid;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

}
