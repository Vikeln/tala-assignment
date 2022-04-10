package com.tala.account.persistence.generators;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

public class AccountNumberGenerator extends SequenceStyleGenerator {

    public static final String DATE_FORMAT_DEFAULT = "%2$tm%2$tY";

    public static final String NUMBER_FORMAT_DEFAULT = "%3$05d";
    private String format;

    @Override
    public Serializable generate(SharedSessionContractImplementor session,
                                 Object object) {
        return String.format(format, "AC", new Date(), super.generate(session, object));
    }

    @Override
    public void configure(Type type, Properties params,
                          ServiceRegistry serviceRegistry) {
        super.configure(LongType.INSTANCE, params, serviceRegistry);
        this.format = "%1$s" + DATE_FORMAT_DEFAULT + NUMBER_FORMAT_DEFAULT;
    }

}
