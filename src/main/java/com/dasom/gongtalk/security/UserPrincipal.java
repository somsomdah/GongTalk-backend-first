package com.dasom.gongtalk.security;

import com.dasom.gongtalk.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class UserPrincipal implements UserDetails {

    private Long id;
    private String deviceNum;
    private String username;
    private String password;

    UserPrincipal(Long id) {
        this.id = id;
    }

    UserPrincipal(Long id, String username) {
        this(id);
        this.username = username;
    }


    UserPrincipal(Long id, String deviceNum, String username, String password) {
        this(id, username);
        this.deviceNum = deviceNum;
        this.password = password;
    }

    public UserPrincipal(User user) {
        this(user.getId(), user.getDeviceNum(), user.getUsername(), user.getPassword());
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getDeviceNum());
    }

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


}
