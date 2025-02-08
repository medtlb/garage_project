package iscae.master.sb.utilisateur.dtos;

import iscae.master.sb.dao.entities.RoleEntity;
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
    private Short idRole;
    private String roleNom;  // To display role name in responses

    public UtilisateurEntity toEntity() {
        return UtilisateurEntity.builder()
                .id(id)
                .nom(nom)
                .email(email)
                .password(password)
                .role(RoleEntity.builder().id(idRole).build())
                .build();
    }

    public static UtilisateurDto fromEntity(UtilisateurEntity utilisateurEntity) {
        return UtilisateurDto.builder()
                .id(utilisateurEntity.getId())
                .nom(utilisateurEntity.getNom())
                .email(utilisateurEntity.getEmail())
                .password(utilisateurEntity.getPassword())
                .idRole(utilisateurEntity.getRole().getId())
                .roleNom(utilisateurEntity.getRole().getNom())
                .build();
    }
}