package com.vit.minibank.dto.request.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "ДТО для получения информации по счету и его истории операций")
public class GetAccountInfoReq {

    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "Некорректный формат номера счета")
    @Schema(description = "Номер счета")
    private String accountNumber;
}
