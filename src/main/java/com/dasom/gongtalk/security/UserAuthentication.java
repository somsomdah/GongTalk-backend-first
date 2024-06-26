package com.dasom.gongtalk.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

public class UserAuthentication extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 560L;
    private final Object principal;
    private Object credentials;

    public UserAuthentication(Object principal) {
        super(null);
        this.principal = principal;
    }

    public UserAuthentication(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }


    public Object getPrincipal() {
        return this.principal;
    }

    public Object getCredentials() {
        return this.credentials;
    }


    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
