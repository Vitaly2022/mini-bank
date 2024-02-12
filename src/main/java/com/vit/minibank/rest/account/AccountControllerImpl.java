package com.vit.minibank.rest.account;

import com.vit.minibank.domain.entity.Account;
import com.vit.minibank.domain.mapper.AccountMapper;
import com.vit.minibank.dto.request.account.AccountCreateReq;
import com.vit.minibank.dto.request.account.AccountTransactionReq;
import com.vit.minibank.dto.request.account.AccountTransferReq;
import com.vit.minibank.dto.request.account.GetAccountInfoReq;
import com.vit.minibank.dto.responce.account.AccountRs;
import com.vit.minibank.service.account.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountControllerImpl implements AccountController {

    private final AccountService service;

    private final AccountMapper mapper;

    @Override
    @PostMapping("/info")
    public AccountRs getInfo(@RequestBody @Valid GetAccountInfoReq request) {
        log.info("[getInfoRq] <-- {}", request);
        Account account = service.getInfo(request);
        AccountRs accountRs = mapper.mapAccountToResponse(account);
        log.info("[getInfoRs] ---> {}", accountRs);
        return accountRs;
    }

    @Override
    @PostMapping("/create")
    public Account createAccount(@RequestBody @Valid AccountCreateReq request) {
        log.info("[createAccountRq] <-- {}", request);
        Account account = service.createAccount(request);

        log.info("[createAccountRs] <-- {}", account);

        return account;
    }

    @Override
    @PostMapping("/replenish")
    public Account replenishBalance(@RequestBody @Valid AccountTransactionReq request) {
        log.info("[replenishBalanceRq] <-- {}", request);
        Account account = service.replenishBalance(request);

        tologResponse(account, "replenishBalanceRs");

        return account;
    }

    @Override
    @PostMapping("/writing")
    public Account writingBalance(@RequestBody @Valid AccountTransactionReq request) {
        log.info("[writingBalanceRq] <-- {}", request);
        Account account = service.withdrawFromAccount(request);

        tologResponse(account, "writingBalanceRs");

        return account;
    }

    @Override
    @PostMapping("/transfer")
    public Account transfer(@RequestBody @Valid AccountTransferReq request) {
        log.info("[transferRq] <-- {}", request);
        Account account = service.transfer(request);

        tologResponse(account, "transferRs");

        return account;
    }


    private void tologResponse(Account account, String method) {
        log.info("[{}] ---> {}, {}", method, account.getUuid(), account.getStatistic().stream()
                .map(statistic -> statistic.getOperationType() + " - " + statistic.getQuantity()).toList());
    }
}
