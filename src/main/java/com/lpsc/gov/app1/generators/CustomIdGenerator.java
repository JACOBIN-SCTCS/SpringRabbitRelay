
package com.lpsc.gov.app1.generators;

import java.io.Serializable;

import com.lpsc.gov.app1.generics.GlobalVariables;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {

        String queryString = "select %s from %s";
        String networkzone = System.getProperty("networkzone");
        
        if (networkzone.equals(GlobalVariables.INTRANET)) {
            queryString = "select max(%s) from %s";
        } else if (networkzone.equals(GlobalVariables.INTERNET)) {
            queryString = "select min(%s) from %s";
        }

        String query = String.format(queryString,
                session.getEntityPersister(obj.getClass().getName(), obj)
                        .getIdentifierPropertyName(),
                obj.getClass().getSimpleName());

        Object object = session.createQuery(query).getSingleResultOrNull();

        long value = 1;
        if (networkzone.equals("INTRANET")) {
            value = (object == null || ((Number) object).longValue() <= 0) ? 1 : ((Number) object).longValue() + 1;
        } else {
            value = (object == null || ((Number) object).longValue() >= 0) ? -1 : ((Number) object).longValue() - 1;
        }

        return value;
    }

    // @Override
    // public void configure(Type type, Properties parameters, ServiceRegistry
    // serviceRegistry) throws MappingException {
    // networkzone = parameters.getProperty("networkzone");
    // }

}
