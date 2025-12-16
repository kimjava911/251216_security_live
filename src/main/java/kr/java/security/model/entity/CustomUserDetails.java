package kr.java.security.model.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// UserDetails -> Spring Security 관리되는 유저 정보의 형태
@Getter
public class CustomUserDetails implements UserDetails {
    // UserAccount -> CustomUserDetails
    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    public CustomUserDetails(UserAccount user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        enabled = user.getEnabled();
        // Collection(list, map...) -> <> -> GrantedAuthority
        authorities = List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    // 내장 메서드들이 추가로 존재...
}
