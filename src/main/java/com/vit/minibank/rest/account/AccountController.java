package com.vit.minibank.rest.account;

import com.vit.minibank.domain.entity.Account;
import com.vit.minibank.dto.request.account.AccountCreateReq;
import com.vit.minibank.dto.request.account.AccountTransactionReq;
import com.vit.minibank.dto.request.account.AccountTransferReq;
import com.vit.minibank.dto.request.account.GetAccountInfoReq;
import com.vit.minibank.dto.responce.account.AccountRs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Account", description = "Управление счетами пользователя")
public interface AccountController {

    @Operation(summary = "Получить информацию по счету с историей операций")
    AccountRs getInfo(GetAccountInfoReq request);

    @Operation(summary = "Создать новый счет для пользователя")
    Account createAccount(AccountCreateReq request);

    @Operation(summary = "Пополнить счет")
    Account replenishBalance(AccountTransactionReq request);

    @Operation(summary = "Снять деньги со счета")
    Account writingBalance(AccountTransactionReq request);

    @Operation(summary = "Перевести деньги на другой счет")
    Account transfer(AccountTransferReq request);
}
