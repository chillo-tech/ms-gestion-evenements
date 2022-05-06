package com.cs.ge.entites;

import com.cs.ge.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Document("UTILISATEURS")
public class Utilisateur implements UserDetails {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean enabled = false;
    private Boolean locked = false;
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public boolean isAccountNonExpired() {
        return true;

    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


}