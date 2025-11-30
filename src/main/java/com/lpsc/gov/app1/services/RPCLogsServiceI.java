package com.lpsc.gov.app1.services;

import com.lpsc.gov.app1.pojo.RPCLogs;

public interface RPCLogsServiceI {
    RPCLogs saveRPCLogs(RPCLogs rpcLogs);

    RPCLogs getRPCLogsById(long id);
}
