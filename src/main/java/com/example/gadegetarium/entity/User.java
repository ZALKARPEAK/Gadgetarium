package com.example.gadegetarium.entity;

import com.example.gadegetarium.entity.adducation.Id;
import com.example.gadegetarium.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends Id implements UserDetails {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ZonedDateTime createDate;
    private ZonedDateTime updateDate;
    private Role role;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.PERSIST})
    private Basket basket;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    private List<Favorite> favorites;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    private List<Comment> comments;

    public void setBasket(Basket basket) {
        this.basket = basket;
        if (basket != null) {
            basket.setUser(this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
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

    public void addComment(Comment comment1) {
        if (comment1 != null) {
            comments.add(comment1);
            comment1.setUser(this);
        } else {
            throw new RuntimeException("Comment is null!!!");
        }
    }
}
