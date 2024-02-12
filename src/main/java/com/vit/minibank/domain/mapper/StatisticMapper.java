package com.vit.minibank.domain.mapper;


import com.vit.minibank.domain.entity.Account;
import com.vit.minibank.domain.entity.Statistic;
import com.vit.minibank.domain.enums.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Component
public class StatisticMapper {

    public Statistic mapStatistic(OperationType type, Account account, BigDecimal quantity) {
        Statistic statistic = new Statistic();

        statistic.setOperationType(type);
        statistic.setSourceAccount(account);
        statistic.setQuantity(quantity);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        statistic.setCreationDate(timestamp);
        statistic.setModificationDate(timestamp);

        return statistic;
    }
}
