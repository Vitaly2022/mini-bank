package com.vit.minibank.rest.user;

import com.vit.minibank.domain.entity.User;
import com.vit.minibank.dto.request.user.UserCreateReq;
import com.vit.minibank.dto.request.user.UserWithAccountsReq;
import com.vit.minibank.dto.responce.user.UserRs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "User", description = "Упровление профилем пользователя")
public interface UserController {

    @Operation(summary = "Найти всех пользователей")
    List<User> findAll();

    @Operation(summary = "Найти пользователя и его счета")
    UserRs findUser(UserWithAccountsReq request);

    @Operation(summary = "Создать пользователя")
    User createUser(UserCreateReq request);
}
