package com.lpsc.gov.app1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lpsc.gov.app1.pojo.RPCCalls;

public interface RPCCallRepo extends JpaRepository<RPCCalls,Long> {
    
}
