package com.dasom.gongtalk.security;

import com.dasom.gongtalk.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
public class Principal implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private String deviceNum;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static Principal create(User user){
        return new Principal(user.getId(), user.getUsername(), user.getPassword(), user.getDeviceNum());
    }



}
