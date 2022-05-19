package com.tala.account.domain.mappers;

import com.tala.account.domain.models.CustomerAccountBalance;
import com.tala.account.domain.models.CustomerAccountState;
import com.tala.account.domain.models.TransactionModel;
import com.tala.account.persistence.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DomainMapper {
    CustomerAccountBalance formatCustomerAccount(CustomerAccountState account);

    @Mappings({@Mapping(target = "amount", source = "transaction.amountTransferred")})
    TransactionModel formatTransactionModelFromTransaction(Transaction transaction);
}
