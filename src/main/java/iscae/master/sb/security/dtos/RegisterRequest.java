package iscae.master.sb.security.dtos;

import iscae.master.sb.dao.entities.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String nom;
    private String email;
    private String password;
    private Role role;
}