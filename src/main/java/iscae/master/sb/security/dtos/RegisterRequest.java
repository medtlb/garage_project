package iscae.master.sb.security.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nom;
    private String email;
    private String password;
    private Short idRole;
}