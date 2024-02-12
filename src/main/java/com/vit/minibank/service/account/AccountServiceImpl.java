package com.vit.minibank.service.account;

import com.vit.minibank.domain.entity.Account;
import com.vit.minibank.domain.entity.User;
import com.vit.minibank.domain.enums.OperationType;
import com.vit.minibank.domain.exceptions.AccountNotFoundException;
import com.vit.minibank.domain.exceptions.InsufficientFundsException;
import com.vit.minibank.domain.exceptions.UserAlreadyExistsException;
import com.vit.minibank.dto.request.account.AccountCreateReq;
import com.vit.minibank.dto.request.account.AccountTransactionReq;
import com.vit.minibank.dto.request.account.AccountTransferReq;
import com.vit.minibank.dto.request.account.GetAccountInfoReq;
import com.vit.minibank.repository.AccountRepository;
import com.vit.minibank.repository.UserRepository;
import com.vit.minibank.service.statistic.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final StatisticService statisticService;

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    @Override
    public Account getInfo(GetAccountInfoReq request) {
        return valid(request.getAccountNumber());
    }

    @Override
    public Account createAccount(AccountCreateReq request) {
        return accountRepository.save(mapAccount(request));
    }

    @Override
    public Account replenishBalance(AccountTransactionReq request) {
        Account account = valid(request.getAccountNumber(), request.getCode());

        account.setBalance(account.getBalance().add(request.getSum()));

        account.setModificationDate(new Timestamp(System.currentTimeMillis()));

        statisticService.saveHistory(OperationType.REPLENISHMENT, account, request.getSum());

        return accountRepository.save(account);
    }

    @Override
    public Account withdrawFromAccount(AccountTransactionReq request) {
        Account account = valid(request.getAccountNumber(), request.getCode());

        if (request.getSum().compareTo(account.getBalance()) == 1) {
            throw new InsufficientFundsException();
        }

        account.setBalance(account.getBalance().subtract(request.getSum()));
        account.setModificationDate(new Timestamp(System.currentTimeMillis()));
        statisticService.saveHistory(OperationType.WITHDRAW, account, request.getSum());

        return accountRepository.save(account);
    }

    @Override
    public Account transfer(AccountTransferReq request) {
        Account account = valid(request.getAccountNumber(), request.getCode());
        Account targetAccount = valid(request.getTargetAccountNumber());

        if (request.getSum().compareTo(account.getBalance()) == 1) {
            throw new InsufficientFundsException();
        }

        account.setBalance(account.getBalance().subtract(request.getSum()));
        targetAccount.setBalance(targetAccount.getBalance().add(request.getSum()));

        Timestamp modificationDate = new Timestamp(System.currentTimeMillis());
        account.setModificationDate(modificationDate);
        targetAccount.setModificationDate(modificationDate);

        statisticService.saveHistory(OperationType.TRANSFER, account, targetAccount, request.getSum());
        statisticService.saveHistory(OperationType.RECEIPT_OF_FUNDS, targetAccount, account, request.getSum());

        accountRepository.save(targetAccount);
        return accountRepository.save(account);
    }

    private Account mapAccount(AccountCreateReq request) {
        Account account = new Account();
        account.setCode(request.getCode());
        account.setBalance(new BigDecimal("0.00"));
        account.setUser(validUser(request.getLogin()));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        account.setCreationDate(timestamp);
        account.setModificationDate(timestamp);

        return account;
    }

    private User validUser(String login) {
        Optional<User> existingUser = userRepository.findByLogin(login);
        if (existingUser.isEmpty()) {
            throw new UserAlreadyExistsException(login);
        }
        return existingUser.get();
    }

    private Account valid(String accountNumber, String code) {
        Optional<Account> accountValid = accountRepository.findByUuidAndCode(UUID.fromString(accountNumber), code);
        return accountValid.orElseThrow(AccountNotFoundException::new);
    }

    private Account valid(String accountNumber) {
        Optional<Account> accountValid = accountRepository.findByUuid(UUID.fromString(accountNumber));
        return accountValid.orElseThrow(AccountNotFoundException::new);
    }
}
