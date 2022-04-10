package com.tala.account.domain.mappers;


import com.tala.account.domain.models.CustomerAccountBalance;
import com.tala.account.persistence.entities.CustomerAccount;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DomainMapper {
    CustomerAccountBalance formatCustomerAccount(CustomerAccount account);
}
