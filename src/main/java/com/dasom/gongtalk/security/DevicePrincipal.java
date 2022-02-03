package com.dasom.gongtalk.security;

import com.dasom.gongtalk.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
public class DevicePrincipal implements UserDetails {

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

    public static DevicePrincipal create(User user){
        return new DevicePrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getDeviceNum());
    }



}
