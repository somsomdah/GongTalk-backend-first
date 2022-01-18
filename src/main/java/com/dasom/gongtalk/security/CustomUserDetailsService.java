package com.dasom.gongtalk.security;
import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.repository.BookmarkRepository;
import com.dasom.gongtalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return DevicePrincipal.create(userRepository.findByUsername(username));
    }

    public UserDetails loadUserById(Integer id) {
        return DevicePrincipal.create(userRepository.findById(id).get());
    }

    public UserDetails loadUserByObject(User user) {
        return DevicePrincipal.create(user);
    }




}
