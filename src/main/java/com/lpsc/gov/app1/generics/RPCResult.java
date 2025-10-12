package com.lpsc.gov.app1.generics;

public class RPCResult {

    private long requestId;;

    private long rabbitmqid;

    private int result;

    public RPCResult() {
        ;
    }
    
    public RPCResult(long requestId, long rabbitmqid, int result) {
        this.requestId = requestId;
        this.rabbitmqid = rabbitmqid;
        this.result = result;
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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    

}
