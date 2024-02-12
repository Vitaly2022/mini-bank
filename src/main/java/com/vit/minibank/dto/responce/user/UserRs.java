package com.vit.minibank.dto.responce.user;

import lombok.Data;

import java.util.List;

@Data
public class UserRs {

    private String name;

    private String login;

    private List<UserAccountForSearchRs> accounts;
}
