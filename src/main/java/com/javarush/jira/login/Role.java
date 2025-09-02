package com.javarush.jira.login;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
// Интерфейс Spring Security.
    DEV,
    ADMIN,
    MANAGER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}