package com.vit.minibank.service.statistic;


import com.vit.minibank.domain.entity.Account;
import com.vit.minibank.domain.enums.OperationType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;

@Tag(name = "Statistic", description = "Управление историей операций")
public interface StatisticService {

    @Operation(summary = "Пополнить аккаунт, снять с аккаунта")
    void saveHistory(OperationType type, Account account, BigDecimal quantity);

    @Operation(summary = "Перевод с одного счета на другой счет")
    void saveHistory(OperationType type, Account sourceAccount, Account targetAccount, BigDecimal quantity);
}
