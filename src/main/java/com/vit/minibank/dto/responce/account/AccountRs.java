package com.vit.minibank.dto.responce.account;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class AccountRs {

    private UUID uuid;

    private BigDecimal balance;

    private List<AccountStatisticRs> statistics;
}
