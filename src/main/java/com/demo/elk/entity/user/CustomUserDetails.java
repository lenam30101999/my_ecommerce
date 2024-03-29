package com.demo.elk.entity.user;

import com.demo.elk.entity.role.Role;
import com.demo.elk.entity.types.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Integer id;

    private String username;

    private String email;

    private String phoneNumber;

    private String uid;

    private Set<String> roles;

    @JsonIgnore
    private String password;

    private List<SimpleGrantedAuthority> authorities;

    public Integer getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
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

    public static CustomUserDetails create(User user) {
        List<SimpleGrantedAuthority> authorities =
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        Set<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getUid(),
                roleNames,
                user.getPassword(),
                authorities
        );
    }
}
