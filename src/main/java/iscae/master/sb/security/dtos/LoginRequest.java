package iscae.master.sb.security.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}