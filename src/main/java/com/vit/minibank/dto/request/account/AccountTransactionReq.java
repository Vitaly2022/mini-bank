package com.vit.minibank.dto.request.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "ДТО для операций: полнение, снятие денежных средств")
public class AccountTransactionReq {

    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^\\d{4}$", message = "Строка должна содержать только четыре цифры")
    @Schema(description = "4-х значный PIN-code")
    private String code;

    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "Некорректный формат номера счета")
    @Schema(description = "Номер счета")
    private String accountNumber;

    @DecimalMin(value = "0.0", inclusive = false)
    @Schema(description = "Сумма перевода")
    private BigDecimal sum;
}
