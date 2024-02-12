package com.vit.minibank.domain.mapper;


import com.vit.minibank.domain.entity.Account;
import com.vit.minibank.domain.entity.Statistic;
import com.vit.minibank.dto.responce.account.AccountRs;
import com.vit.minibank.dto.responce.account.AccountStatisticRs;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountMapper {

    public AccountRs mapAccountToResponse(Account account) {
        AccountRs response = new AccountRs();
        response.setUuid(account.getUuid());
        response.setBalance(account.getBalance());

        List<AccountStatisticRs> statisticResponses = mapAccountStatistic(account.getStatistic());
        response.setStatistics(statisticResponses);

        return response;
    }

    private List<AccountStatisticRs> mapAccountStatistic(List<Statistic> statistics) {
        return statistics.stream()
                .map(statistic -> {
                    AccountStatisticRs statisticResponse = new AccountStatisticRs();
                    statisticResponse.setOperationType(statistic.getOperationType());
                    statisticResponse.setQuantity(statistic.getQuantity());
                    statisticResponse.setTargetAccount(statistic.getTargetAccount());
                    return statisticResponse;
                }).toList();

    }
}
