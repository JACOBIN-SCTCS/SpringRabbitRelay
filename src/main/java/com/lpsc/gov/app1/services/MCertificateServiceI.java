package com.lpsc.gov.app1.services;

import com.lpsc.gov.app1.pojo.A;
import com.lpsc.gov.app1.pojo.MCertificate;

public interface MCertificateServiceI {
    MCertificate saveMCertificate(MCertificate mCertificate);

    MCertificate getMCertificateById(long id);

    A getAcertificate(long id);
}
