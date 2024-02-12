package com.vit.minibank.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "ДТО для получения пользователя и всех его счетов")
public class UserWithAccountsReq {

    @Size(max = 100, message = "Длина строки 'login' не должна превышать 100 символов")
    @NotBlank(message = "Поле не должно быть пустым")
    @Schema(description = "Уникальный логин")
    private String login;
}
