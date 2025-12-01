package com.auth.Auth.App.Dto;

import jakarta.persistence.Column;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {

    private UUID id ;
    private  String name;

}
