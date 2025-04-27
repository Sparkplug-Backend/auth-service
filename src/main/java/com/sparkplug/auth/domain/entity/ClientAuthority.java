package com.sparkplug.auth.domain.entity;

import com.sparkplug.auth.domain.enums.ClientRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "client_authority", schema = "auth")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "client_authority_client",
            joinColumns = @JoinColumn(name = "client_authority_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id"))
    private List<Client> clients;

    public static ClientAuthority create(ClientRole clientRole) {
        return ClientAuthority.builder()
                .name(clientRole.toString())
                .build();
    }

    @Override
    public String toString() {
        return name;
    }
}
