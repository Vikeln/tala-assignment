package com.tala.account.persistence.generators;

import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class IpAddressGenerator implements ValueGenerator<String> {


    @Override
    public String generateValue(Session session, Object object) {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
        } catch (Exception ex) {
            return null;
        }
    }
}
