package com.auth.Auth.App.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id", updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();
    @Column(name = "role_name", unique = true, nullable = false)
    private  String name;
}
