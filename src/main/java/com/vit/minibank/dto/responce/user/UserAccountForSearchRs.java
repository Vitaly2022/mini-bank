package com.vit.minibank.dto.responce.user;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class UserAccountForSearchRs {

    private UUID uuid;

    private BigDecimal balance;
}
