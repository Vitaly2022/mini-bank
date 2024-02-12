package com.vit.minibank.service.account;

import com.vit.minibank.domain.entity.Account;
import com.vit.minibank.domain.entity.User;
import com.vit.minibank.domain.enums.OperationType;
import com.vit.minibank.dto.request.account.AccountCreateReq;
import com.vit.minibank.dto.request.account.AccountTransactionReq;
import com.vit.minibank.dto.request.account.AccountTransferReq;
import com.vit.minibank.dto.request.account.GetAccountInfoReq;
import com.vit.minibank.repository.AccountRepository;
import com.vit.minibank.repository.UserRepository;
import com.vit.minibank.service.statistic.StatisticService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {
    @Mock
    private StatisticService statisticService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private static final String ACCOUNT_NUMBER = "0f83304b-3fd1-4d6c-8ef4-82faad7aadaf";
    private static final UUID ACCOUNT_UUID = UUID.fromString("0f83304b-3fd1-4d6c-8ef4-82faad7aadaf");
    private static final String CODE = "5555";
    private static final String LOGIN = "testUser";
    private static final BigDecimal SUM = new BigDecimal(100.0);

    @Test
    public void testGetInfo() {
        // Заглушка для метода findByUuid
        when(accountRepository.findByUuid(any(UUID.class))).thenReturn(Optional.of(createTestAccount()));

        // Вызываем метод и проверяем результат
        GetAccountInfoReq request = new GetAccountInfoReq();
        request.setAccountNumber(ACCOUNT_NUMBER);
        Account account = accountService.getInfo(request);

        assertNotNull(account);
        assertEquals(ACCOUNT_NUMBER, account.getUuid().toString());

        // Проверяем, что метод findByUuid был вызван ровно один раз
        verify(accountRepository, times(1)).findByUuid(any(UUID.class));
    }

    @Test
    public void testAccountCreate() {
        AccountCreateReq request = new AccountCreateReq();
        request.setCode(CODE);
        request.setLogin(LOGIN);

        when(userRepository.findByLogin(LOGIN)).thenReturn(Optional.of(createTestUser()));

        Account account = accountService.createAccount(request);

        assertNull(account);

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testReplenishBalance() {
        // Заглушка для метода valid
        when(accountRepository.findByUuidAndCode(any(UUID.class), any(String.class))).thenReturn(Optional.of(createTestAccount()));

        // Заглушка для метода save
        when(accountRepository.save(any(Account.class))).thenReturn(createTestAccount());

        // Создаем запрос
        AccountTransactionReq request = new AccountTransactionReq();
        request.setAccountNumber(ACCOUNT_NUMBER);
        request.setCode(CODE);
        request.setSum(SUM);

        // Вызываем метод и проверяем результат
        Account account = accountService.replenishBalance(request);

        assertNotNull(account);
        assertEquals(SUM, account.getBalance());

        // Проверяем, что метод valid был вызван ровно один раз
        verify(accountRepository, times(1)).findByUuidAndCode(any(UUID.class), any(String.class));

        // Проверяем, что метод save был вызван ровно один раз
        verify(accountRepository, times(1)).save(any(Account.class));

        // Проверяем, что метод saveHistory был вызван ровно один раз
        verify(statisticService, times(1)).saveHistory(eq(OperationType.REPLENISHMENT), any(Account.class), eq(SUM));
    }

    @Test
    public void testWritingBalance() {
        // Заглушка для метода valid
        when(accountRepository.findByUuidAndCode(eq(ACCOUNT_UUID), any(String.class))).thenReturn(Optional.of(createTestAccount()));

        // Заглушка для метода save
        when(accountRepository.save(any(Account.class))).thenReturn(createTestAccount());

        // Создаем запрос
        AccountTransactionReq request = new AccountTransactionReq();
        request.setAccountNumber(ACCOUNT_NUMBER);
        request.setCode(CODE);
        request.setSum(SUM);

        // Вызываем метод и проверяем результат
        Account account = accountService.withdrawFromAccount(request);

        assertNotNull(account);

        // Проверяем, что метод valid был вызван ровно один раз
        verify(accountRepository, times(1)).findByUuidAndCode(eq(ACCOUNT_UUID), any(String.class));

        // Проверяем, что метод save был вызван ровно один раз
        verify(accountRepository, times(1)).save(any(Account.class));

        // Проверяем, что метод saveHistory был вызван ровно один раз
        verify(statisticService, times(1)).saveHistory(eq(OperationType.WITHDRAW), any(Account.class), eq(SUM));
    }

    @Test
    public void testTransfer() {
        // Заглушка для метода valid
        when(accountRepository.findByUuidAndCode(any(UUID.class), any(String.class))).thenReturn(Optional.of(createTestAccount()));
        when(accountRepository.findByUuid(any(UUID.class))).thenReturn(Optional.of(createTestAccount()));

        // Заглушка для метода save
        when(accountRepository.save(any(Account.class))).thenReturn(createTestAccount());

        // Создаем запрос
        AccountTransferReq request = new AccountTransferReq();
        request.setAccountNumber(ACCOUNT_NUMBER);
        request.setCode(CODE);
        request.setTargetAccountNumber("0f83304b-3fd1-4d6c-8ef4-82faad7aadaf");
        request.setSum(SUM);

        // Вызываем метод и проверяем результат
        Account account = accountService.transfer(request);

        assertNotNull(account);

        verify(accountRepository, times(1)).findByUuidAndCode(any(UUID.class), eq(CODE));
        verify(accountRepository, times(2)).save(any(Account.class));

        verify(statisticService, times(1)).saveHistory(eq(OperationType.TRANSFER), any(Account.class), any(Account.class), eq(SUM));
        verify(statisticService, times(1)).saveHistory(eq(OperationType.RECEIPT_OF_FUNDS), any(Account.class), any(Account.class), eq(SUM));
    }

    private Account createTestAccount() {
        Account account = new Account();
        account.setUuid(UUID.fromString(ACCOUNT_NUMBER));
        account.setCode(CODE);
        account.setBalance(new BigDecimal(100.0));
        account.setUser(createTestUser());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        account.setCreationDate(timestamp);
        account.setModificationDate(timestamp);

        return account;
    }

    private User createTestUser() {
        User user = new User();
        user.setLogin(LOGIN);
        user.setName("Test User");
        user.setCreationDate(new Timestamp(System.currentTimeMillis()));
        user.setModificationDate(new Timestamp(System.currentTimeMillis()));
        return user;
    }
}