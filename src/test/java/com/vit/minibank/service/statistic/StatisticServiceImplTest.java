package com.vit.minibank.service.statistic;

import com.vit.minibank.domain.entity.Account;
import com.vit.minibank.domain.entity.Statistic;
import com.vit.minibank.domain.enums.OperationType;
import com.vit.minibank.domain.mapper.StatisticMapper;
import com.vit.minibank.repository.StatisticRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticServiceImplTest {
    @InjectMocks
    private StatisticServiceImpl statisticService;

    @Mock
    private StatisticRepository repository;

    @Mock
    private StatisticMapper mapper;

    @Test
    public void testSaveHistoryWithOneAccount() {

        OperationType type = OperationType.REPLENISHMENT;
        Account account = new Account();
        BigDecimal quantity = BigDecimal.valueOf(100);

        Statistic expectedStatistic = new Statistic();
        when(mapper.mapStatistic(type, account, quantity)).thenReturn(expectedStatistic);

        statisticService.saveHistory(type, account, quantity);

        verify(mapper).mapStatistic(type, account, quantity);
        verify(repository).save(expectedStatistic);
    }

    @Test
    public void testSaveHistoryWithTwoAccount() {

        OperationType type = OperationType.TRANSFER;
        Account sourceAccount = new Account();
        Account targetAccount = new Account();
        BigDecimal quantity = BigDecimal.valueOf(500);

        Statistic expectedStatistic = new Statistic();
        when(mapper.mapStatistic(type, sourceAccount, quantity)).thenReturn(expectedStatistic);

        statisticService.saveHistory(type, sourceAccount, targetAccount, quantity);

        verify(mapper).mapStatistic(type, sourceAccount, quantity);
        verify(repository).save(expectedStatistic);
        assertEquals(targetAccount.getUuid(), expectedStatistic.getTargetAccount());
    }

}