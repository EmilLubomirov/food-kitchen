package com.example.foodkitchen.api.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String username;
    private String id;
    private Collection<? extends GrantedAuthority> authorities;
    private String avatarImageUrl;

    public JwtResponse(String accessToken,
                       String id,
                       String username,
                       Collection<? extends GrantedAuthority> authorities,
                       String avatarImageUrl) {
        this.id = id;
        this.token = accessToken;
        this.username = username;
        this.authorities = authorities;
        this.avatarImageUrl = avatarImageUrl;
    }
}
