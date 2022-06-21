package com.dasom.gongtalk.security;

import com.dasom.gongtalk.domain.User;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserPrincipal.create(getFromUsername(username));
    }

    public UserPrincipal loadUserById(Integer id) {
        return UserPrincipal.create(getFromId(id));
    }

    public UserPrincipal loadUserByObject(User user) {
        return UserPrincipal.create(user);
    }


    public User getFromId(Integer id){

        Optional<User> user = userRepository.findById(id);
        try{
            return user.get();
        }catch (Exception e){
            throw new ResourceNotFoundException("user", "id", id);
        }

    }

    public User getFromUsername(String username){

        Optional<User> user = userRepository.findByUsername(username);
        try{
            return user.get();
        }catch (Exception e){
            throw new ResourceNotFoundException("user", "username", username);
        }

    }




}
