package com.vit.minibank.service.user;

import com.vit.minibank.domain.entity.User;
import com.vit.minibank.domain.exceptions.UserAlreadyExistsException;
import com.vit.minibank.domain.exceptions.UserNotFoundException;
import com.vit.minibank.domain.mapper.UserMapper;
import com.vit.minibank.dto.request.user.UserCreateReq;
import com.vit.minibank.dto.request.user.UserWithAccountsReq;
import com.vit.minibank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public User findUser(UserWithAccountsReq request) {
        Optional<User> existingUser = repository.findByLogin(request.getLogin());
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException(request.getLogin());
        }
        return existingUser.get();
    }

    @Override
    public User createUser(UserCreateReq request) {
        Optional<User> existingUser = repository.findByLogin(request.getLogin());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(request.getLogin());
        }
        User user = mapper.mapUserFromRequest(request);

        return repository.save(user);
    }
}

