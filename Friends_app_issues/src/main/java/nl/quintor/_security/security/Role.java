package nl.quintor._security.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public enum Role {
    USER(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))),
    ADMIN(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))),
    COMMUNITY_MANAGER(Collections.singletonList(new SimpleGrantedAuthority("ROLE_COMMUNITY_MANAGER")));

    Random r = new Random(System.currentTimeMillis());

    private final Collection<GrantedAuthority> authorities;
    private final int accessCode;

    public int getAccessCode() {
        return accessCode;
    }

    Role(Collection<GrantedAuthority> authorities) {
        this.accessCode = r.nextInt();
        this.authorities = authorities;
    }

    public Collection<GrantedAuthority> getAuthorities() { return authorities; }

}
