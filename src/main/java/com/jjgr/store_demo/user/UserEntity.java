package com.jjgr.store_demo.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.jjgr.store_demo.security.RoleEntity;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Email(message = "Invalid email")
    private String email;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_account_non_expired")
    private boolean accountNotExpired;

    @Column(name = "is_account_non_locked")
    private boolean accountNotLocked;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    public UserEntity(String name, String password, String email, boolean isActive, boolean accountNotExpired, boolean accountNotLocked) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.accountNotExpired = accountNotExpired;
        this.accountNotLocked = accountNotLocked;
    }


}