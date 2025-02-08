package iscae.master.sb.role.dtos;

import iscae.master.sb.dao.entities.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Short id;
    private String nom;

    public RoleEntity toEntity() {
        return RoleEntity.builder()
                .id(id)
                .nom(nom)
                .build();
    }

    public static RoleDto fromEntity(RoleEntity roleEntity) {
        return RoleDto.builder()
                .id(roleEntity.getId())
                .nom(roleEntity.getNom())
                .build();
    }
}