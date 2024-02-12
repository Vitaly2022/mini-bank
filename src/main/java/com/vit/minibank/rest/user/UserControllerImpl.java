package com.vit.minibank.rest.user;

import com.vit.minibank.domain.entity.User;
import com.vit.minibank.domain.mapper.UserMapper;
import com.vit.minibank.dto.request.user.UserCreateReq;
import com.vit.minibank.dto.request.user.UserWithAccountsReq;
import com.vit.minibank.dto.responce.user.UserRs;
import com.vit.minibank.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService service;

    private final UserMapper mapper;

    @Override
    @GetMapping("/all")
    public List<User> findAll() {
        return service.findAllUsers();
    }

    @Override
    @PostMapping("/info")
    public UserRs findUser(@RequestBody @Valid UserWithAccountsReq request) {
        log.info("[findUserRq] <-- {}", request);
        User user = service.findUser(request);
        UserRs userRs = mapper.mapUserToUserResponse(user);
        log.info("[findUserRs] ---> {}", userRs);
        return userRs;
    }

    @Override
    @PostMapping("/create")
    public User createUser(@RequestBody @Valid UserCreateReq request) {
        log.info("[createUserRq] <--- {}", request);
        User user = service.createUser(request);
        log.info("[createUserRs] ---> {}", user);
        return user;
    }
}
