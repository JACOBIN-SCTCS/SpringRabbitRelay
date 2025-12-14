package com.lpsc.gov.app1.pojo;

import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rpccalls")
public class RPCCalls {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long rpcid;

    @Column(name = "instancename")
    private String instancename;

    @Column(name = "servicename")
    private String serviceName;

    @Column(name = "methodname")
    private String methodName;

    @Column(name = "rabbitmqid")
    private long rabbitmqid;

    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Column(name = "result")
    private int result = -1;

    @Column(name = "createdDate")
    private Timestamp createdDate = new Timestamp(System.currentTimeMillis());

    @Column(name = "resultDate")
    private Timestamp resultDate;

    public long getRpcid() {
        return rpcid;
    }

    public void setRpcid(long rpcid) {
        this.rpcid = rpcid;
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

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getResultDate() {
        return resultDate;
    }

    public void setResultDate(Timestamp resultDate) {
        this.resultDate = resultDate;
    }

    public String getInstancename() {
        return instancename;
    }

    public void setInstancename(String instancename) {
        this.instancename = instancename;
    }

}
