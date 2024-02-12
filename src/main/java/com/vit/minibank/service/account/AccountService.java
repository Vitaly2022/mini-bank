package com.vit.minibank.service.account;


import com.vit.minibank.domain.entity.Account;
import com.vit.minibank.dto.request.account.AccountCreateReq;
import com.vit.minibank.dto.request.account.AccountTransactionReq;
import com.vit.minibank.dto.request.account.AccountTransferReq;
import com.vit.minibank.dto.request.account.GetAccountInfoReq;

public interface AccountService {

    Account getInfo(GetAccountInfoReq request);

    Account createAccount(AccountCreateReq request);

    Account replenishBalance(AccountTransactionReq request);

    Account withdrawFromAccount(AccountTransactionReq request);

    Account transfer(AccountTransferReq request);
}
