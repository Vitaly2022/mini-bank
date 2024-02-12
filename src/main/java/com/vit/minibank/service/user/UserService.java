package com.vit.minibank.service.user;

import com.vit.minibank.domain.entity.User;
import com.vit.minibank.dto.request.user.UserCreateReq;
import com.vit.minibank.dto.request.user.UserWithAccountsReq;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findUser(UserWithAccountsReq request);

    User createUser(UserCreateReq request);
}
