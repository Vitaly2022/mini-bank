package com.vit.minibank.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "ДТО для создания пользователя")
public class UserCreateReq {

    @Size(max = 30, message = "Длина строки 'name' не должна превышать 100 символов")
    @NotBlank(message = "Поле не должно быть пустым")
    @Schema(description = "Имя пользователя")
    private String name;

    @Size(max = 100, message = "Длина строки 'login' не должна превышать 100 символов")
    @NotBlank(message = "Поле не должно быть пустым")
    @Schema(description = "Логин")
    private String login;
}
