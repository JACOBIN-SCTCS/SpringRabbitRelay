
package com.lpsc.gov.app1.dto;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpsc.gov.app1.generics.GlobalVariables;
import com.lpsc.gov.app1.pojo.MCertificate;
import com.lpsc.gov.app1.pojo.MCheckListEntry;
import com.lpsc.gov.app1.pojo.MCheckListItem;
import com.lpsc.gov.app1.pojo.MCheckListItemValue;
import com.lpsc.gov.app1.pojo.MReview;

import org.hibernate.Session;

public class CertificateDTO extends TransferDTO {

    public MCertificate mCertificate;

    public CertificateDTO() {
        super(GlobalVariables.CERTIFICATE_DTO);

    }

    private <T, ID extends Serializable> void saveIfNotExists(
            Session session,
            Class<T> entityClass,
            T entity,
            Function<T, ID> idExtractor,
            boolean update) {
        try {

            if (entity == null)
                return;

            ID id = idExtractor.apply(entity);
            System.out.println("********id = " + id + " classname =" + entityClass);
            if (id == null || (Long) id == 0) {
                session.persist(entity);
                return;
            }
            T existing = session.get(entityClass, id);
            if (existing == null) {
                session.save(entity);
            } else {
                if (update)
                    session.merge(entity);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
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

    private void saveCheckListItem(Session session, MCheckListItem checkListItem) {
        try {
            saveIfNotExists(session, MCheckListItem.class, checkListItem, MCheckListItem::getId, true);
            List<MCheckListItemValue> checkListItemValues = checkListItem.getCheckListItemValues();
            if (checkListItemValues != null) {
                for (int i = 0; i < checkListItemValues.size(); i++) {
                    saveIfNotExists(session, MCheckListItemValue.class, checkListItemValues.get(i),
                            MCheckListItemValue::getId, true);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void saveMReview(Session session, MReview mReview) {
        try {
            saveIfNotExists(session, MReview.class, mReview, MReview::getId, true);
            List<MCheckListEntry> checkListEntries = mReview.getCheckListEntries();
            if (checkListEntries != null) {
                for (int i = 0; i < checkListEntries.size(); i++) {
                    MCheckListEntry mCheckListEntry = checkListEntries.get(i);
                    if (mCheckListEntry.getCheckListItem() != null) {
                        saveCheckListItem(session, mCheckListEntry.getCheckListItem());
                    }
                    saveIfNotExists(session, MCheckListEntry.class, mCheckListEntry, MCheckListEntry::getId, true);
                }
            }

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean saveData(Session session) {
        try {
            saveMReview(session, mCertificate.getmReview());
            saveIfNotExists(session, MCertificate.class, mCertificate, MCertificate::getId, true);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

}
