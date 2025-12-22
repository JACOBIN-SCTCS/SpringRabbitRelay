
package com.lpsc.gov.app1.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpsc.gov.app1.generics.GlobalVariables;
import com.lpsc.gov.app1.pojo.A;
import com.lpsc.gov.app1.pojo.B;

import org.hibernate.Session;

public class ADTO extends TransferDTO {

    public ADTO() {
        super(GlobalVariables.A_DTO);
    }

    public A a;

    public String convertToMessage() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{}";
        try {
            jsonString = mapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonString;

    }

    @Override
    public String testmethod() {
        throw new UnsupportedOperationException("Unimplemented method 'testmethod'");
    }

    @Override
    public boolean saveData(Session session) {

        List<B> bEntries = a.getbEntries();

        a.setbEntries(null);
        A dBEntry = session.get(A.class, a.getId());
        if (dBEntry == null) {
            dBEntry = new A();
            dBEntry.setId(a.getId());
            dBEntry.setTitle(a.getTitle());
            session.save(dBEntry);

        } else {
            dBEntry.setId(a.getId());
            dBEntry.setTitle(a.getTitle());
            session.update(dBEntry);
        }

        session.flush();

        List<B> newBEntries = new ArrayList<>();
        for (int i = 0; i < bEntries.size(); ++i) {
            B dtoBEntry = bEntries.get(i);
            B persistent = session.get(B.class, dtoBEntry.getId());
            if (persistent == null) {
                persistent = new B();
                persistent.setBstring(dtoBEntry.getBstring());
                persistent.setId(dtoBEntry.getId());
                session.save(dtoBEntry);
            } else {
                persistent.setBstring(dtoBEntry.getBstring());
                persistent.setId(dtoBEntry.getId());
                session.update(persistent);
            }
        }

        session.flush();

        dBEntry = session.get(A.class, a.getId());

        for (int i = 0; i < bEntries.size(); ++i) {
            B persistent = session.get(B.class, bEntries.get(i).getId());
            persistent.setaEntry(dBEntry);
            session.saveOrUpdate(persistent);
        }

        // dBEntry.setbEntries(newBEntries);
        // session.merge(dBEntry);
        session.flush();
        return true;

    }
}
