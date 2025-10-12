package com.lpsc.gov.app1.generics;

import java.util.Map;

public class RPCPayload {
    
    private long requestId;

    private String serviceName;

    private String methodName;

    private long rabbitmqid;

    private Map<String,Object> params;

    public RPCPayload() {
        ;
    }
    
    public RPCPayload(long requestId, String serviceName, String methodName, long rabbitmqid,
            Map<String, Object> params) {
        this.requestId = requestId;
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.rabbitmqid = rabbitmqid;
        this.params = params;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getRabbitmqid() {
        return rabbitmqid;
    }

    public void setRabbitmqid(long rabbitmqid) {
        this.rabbitmqid = rabbitmqid;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    

    


}
