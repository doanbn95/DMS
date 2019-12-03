package com.omi.sakura.persistent.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserPrincipal extends User {

    private com.omi.sakura.persistent.domain.User user;

    public UserPrincipal(com.omi.sakura.persistent.domain.User user, boolean enabled, boolean accountNonExpired,
                         boolean credentialsNonExpired,
                         boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities) {
        super(user.getUserName(), user.getPassword(),
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
    }

    public com.omi.sakura.persistent.domain.User getUser() {
        return this.user;
    }
}
