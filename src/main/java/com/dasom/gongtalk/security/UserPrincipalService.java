package com.dasom.gongtalk.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrincipalService {

    public UserPrincipal loadUserPrincipalById(Long id) {
        return new UserPrincipal(id);
    }


}
