package com.vit.minibank.dto.responce.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vit.minibank.domain.enums.OperationType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountStatisticRs {

    private OperationType operationType;

    private BigDecimal quantity;

    private UUID targetAccount;
}
