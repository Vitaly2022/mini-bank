package com.vit.minibank.service.statistic;

import com.vit.minibank.domain.entity.Account;
import com.vit.minibank.domain.entity.Statistic;
import com.vit.minibank.domain.enums.OperationType;
import com.vit.minibank.domain.mapper.StatisticMapper;
import com.vit.minibank.repository.StatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository repository;

    private final StatisticMapper mapper;

    @Override
    public void saveHistory(OperationType type, Account account, BigDecimal quantity) {
        Statistic statistic = mapper.mapStatistic(type, account, quantity);

        repository.save(statistic);
    }

    @Override
    public void saveHistory(OperationType type, Account sourceAccount, Account targetAccount, BigDecimal quantity) {
        Statistic statistic = mapper.mapStatistic(type, sourceAccount, quantity);
        statistic.setTargetAccount(targetAccount.getUuid());

        repository.save(statistic);
    }
}
