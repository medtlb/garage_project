package iscae.master.sb.utilisateur.dtos;

import iscae.master.sb.dao.entities.Role;
import iscae.master.sb.dao.entities.UtilisateurEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDto {
    private Long id;
    private String nom;
    private String email;
    private String password;
    private Role role;

    public UtilisateurEntity toEntity() {
        return UtilisateurEntity.builder()
                .id(id)
                .nom(nom)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    public static UtilisateurDto fromEntity(UtilisateurEntity utilisateurEntity) {
        return UtilisateurDto.builder()
                .id(utilisateurEntity.getId())
                .nom(utilisateurEntity.getNom())
                .email(utilisateurEntity.getEmail())
                .password(utilisateurEntity.getPassword())
                .role(utilisateurEntity.getRole())
                .build();
    }
}