package com.lpsc.gov.app1.services;

import com.lpsc.gov.app1.pojo.MCertificate;
import com.lpsc.gov.app1.repository.MCertificateRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("MCertificateService")
public class MCertificateService implements MCertificateServiceI {

    @Autowired
    private MCertificateRepo mcertificateRepo;

    @Override
    public MCertificate saveMCertificate(MCertificate mCertificate) {
        return mcertificateRepo.save(mCertificate);
    }

}
