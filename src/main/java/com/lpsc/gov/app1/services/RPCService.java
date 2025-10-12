package com.lpsc.gov.app1.services;

import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lpsc.gov.app1.pojo.RPCCalls;
import com.lpsc.gov.app1.repository.RPCCallRepo;

@Service("rpcService")
@Transactional
public class RPCService implements RPCServiceI {

    @Autowired
    private RPCCallRepo rpcCallRepo;

    @Override
    public RPCCalls updateCall(RPCCalls rpcCall) {
        return rpcCallRepo.save(rpcCall);
    }

    @Override
    public RPCCalls addNewCall(String serviceName, String methodName, String payload) {
        RPCCalls rpcCall = new RPCCalls();
        rpcCall.setMethodName(methodName);
        rpcCall.setServiceName(serviceName);
        rpcCall.setPayload(payload);
        return rpcCallRepo.save(rpcCall);
    }

    @Override
    @Transactional
    public RPCCalls getRPCCallById(long reqId) {
        RPCCalls rpcall = rpcCallRepo.getById(reqId);
        Hibernate.initialize(rpcall);
        return rpcall;
    }

}
