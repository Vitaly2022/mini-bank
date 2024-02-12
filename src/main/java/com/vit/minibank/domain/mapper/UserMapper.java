package com.vit.minibank.domain.mapper;

import com.vit.minibank.domain.entity.Account;
import com.vit.minibank.domain.entity.User;
import com.vit.minibank.dto.request.user.UserCreateReq;
import com.vit.minibank.dto.responce.user.UserAccountForSearchRs;
import com.vit.minibank.dto.responce.user.UserRs;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class UserMapper {

    public UserRs mapUserToUserResponse(User user) {
        UserRs userResponse = new UserRs();
        userResponse.setName(user.getName());
        userResponse.setLogin(user.getLogin());

        List<UserAccountForSearchRs> accountResponses = user.getAccounts().stream()
                .map(this::mapAccountToUserAccountForSearchResponse).toList();
        userResponse.setAccounts(accountResponses);
        return userResponse;
    }

    public User mapUserFromRequest(UserCreateReq request) {
        User user = new User();

        user.setName(request.getName());
        user.setLogin(request.getLogin());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setCreationDate(timestamp);
        user.setModificationDate(timestamp);

        return user;
    }

    private UserAccountForSearchRs mapAccountToUserAccountForSearchResponse(Account account) {
        UserAccountForSearchRs accountResponse = new UserAccountForSearchRs();
        accountResponse.setUuid(account.getUuid());
        accountResponse.setBalance(account.getBalance());
        return accountResponse;
    }
}
