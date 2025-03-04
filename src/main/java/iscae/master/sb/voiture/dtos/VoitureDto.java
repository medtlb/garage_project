package iscae.master.sb.voiture.dtos;

import iscae.master.sb.dao.entities.UtilisateurEntity;
import iscae.master.sb.dao.entities.VoitureEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoitureDto {
    private Long id;
    private Long userId;
    private String userNom;  // To display user name in responses
    private String immatriculation;
    private String marque;
    private String model;
    private String image;

    public VoitureEntity toEntity() {
        return VoitureEntity.builder()
                .id(id)
                .utilisateur(UtilisateurEntity.builder().id(userId).build())
                .immatriculation(immatriculation)
                .marque(marque)
                .model(model)
                .image(image)
                .build();
    }

    public static VoitureDto fromEntity(VoitureEntity voitureEntity) {
        return VoitureDto.builder()
                .id(voitureEntity.getId())
                .userId(voitureEntity.getUtilisateur().getId())
                .userNom(voitureEntity.getUtilisateur().getNom())
                .immatriculation(voitureEntity.getImmatriculation())
                .marque(voitureEntity.getMarque())
                .model(voitureEntity.getModel())
                .image(voitureEntity.getImage())
                .build();
    }
}