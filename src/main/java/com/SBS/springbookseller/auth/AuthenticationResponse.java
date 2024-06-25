package com.SBS.springbookseller.auth;

import com.SBS.springbookseller.DAO.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private Long Id;
    private String username;
    private String email;
    private Role role;
    private String token;
}
