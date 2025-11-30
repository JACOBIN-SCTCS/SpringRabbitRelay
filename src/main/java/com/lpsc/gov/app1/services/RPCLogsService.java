package com.lpsc.gov.app1.services;

import com.lpsc.gov.app1.pojo.RPCLogs;
import com.lpsc.gov.app1.repository.RPCLogsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("RPCLogsService")
public class RPCLogsService implements RPCLogsServiceI {

    @Autowired
    private RPCLogsRepo rpcLogsRepo;

    @Override
    public RPCLogs saveRPCLogs(RPCLogs rpcLogs) {
        return rpcLogsRepo.save(rpcLogs);
    }

    @Override
    public RPCLogs getRPCLogsById(long id) {
        return rpcLogsRepo.findById(id).get();
    }

}
