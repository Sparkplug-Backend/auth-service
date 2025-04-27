package com.sparkplug.auth.domain.entity;

import com.sparkplug.auth.domain.enums.AdminRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "admin_authority", schema = "auth")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "admin_authority_admin",
            joinColumns = @JoinColumn(name = "admin_authority_id"),
            inverseJoinColumns = @JoinColumn(name = "admin_id"))
    private List<Admin> admins;

    public static AdminAuthority create(AdminRole adminRole) {
        return AdminAuthority.builder()
                .name(adminRole.toString())
                .build();
    }

    @Override
    public String toString() {
        return name;
    }
}
