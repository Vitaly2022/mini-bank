package com.vit.minibank.dto.request.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "ДТО для создания счета для указанного логина")
public class AccountCreateReq {

    @Size(max = 100, message = "Длина строки 'login' не должна превышать 100 символов")
    @NotBlank(message = "Поле не должно быть пустым")
    @Schema(description = "Уникальный логин пользователя, которому открывается счет")
    private String login;

    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^\\d{4}$", message = "Строка должна содержать только 4 цифры")
    @Schema(description = "4-х значный PIN-code")
    private String code;

}
