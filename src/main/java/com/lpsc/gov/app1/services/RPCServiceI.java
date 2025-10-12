package com.lpsc.gov.app1.services;

import java.util.Map;

import com.lpsc.gov.app1.pojo.RPCCalls;

public interface RPCServiceI {
    RPCCalls addNewCall(String serviceName, String methodName, String payload);
    RPCCalls updateCall(RPCCalls rpcCall);
    RPCCalls getRPCCallById(long reqId);
}
