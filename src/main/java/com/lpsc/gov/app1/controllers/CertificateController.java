package com.lpsc.gov.app1.controllers;

import java.util.Random;

import com.lpsc.gov.app1.pojo.MCertificate;
import com.lpsc.gov.app1.services.MCertificateServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CertificateController {

    @Autowired
    private MCertificateServiceI mCertificateService;

    private String getRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    @GetMapping("/populateCertificate")
    public String populateCertificate() {
        MCertificate mCertificate = new MCertificate();
        mCertificate.setCertString(getRandomString(15));
        mCertificate.setActive(1);

        mCertificateService.saveMCertificate(mCertificate);
        return "populated a certificate";
    }
}
