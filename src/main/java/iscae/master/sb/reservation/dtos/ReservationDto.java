package iscae.master.sb.reservation.dtos;

import iscae.master.sb.dao.entities.GarageEntity;
import iscae.master.sb.dao.entities.ReservationEntity;
import iscae.master.sb.dao.entities.UtilisateurEntity;
import iscae.master.sb.dao.entities.VoitureEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private Long id;
    private Long userId;
    private String userNom;
    private Long garageId;
    private String garageNom;
    private Long voitureId;
    private String voitureImmatriculation;
    private LocalDateTime dateReservation;
    private String description;
    private String status;
    private Double prixEstime;

    public ReservationEntity toEntity() {
        return ReservationEntity.builder()
                .id(id)
                .utilisateur(UtilisateurEntity.builder().id(userId).build())
                .garage(GarageEntity.builder().id(garageId).build())
                .voiture(VoitureEntity.builder().id(voitureId).build())
                .dateReservation(dateReservation)
                .description(description)
                .status(status)
                .prixEstime(prixEstime)
                .build();
    }

    public static ReservationDto fromEntity(ReservationEntity entity) {
        return ReservationDto.builder()
                .id(entity.getId())
                .userId(entity.getUtilisateur().getId())
                .userNom(entity.getUtilisateur().getNom())
                .garageId(entity.getGarage().getId())
                .garageNom(entity.getGarage().getNom())
                .voitureId(entity.getVoiture().getId())
                .voitureImmatriculation(entity.getVoiture().getImmatriculation())
                .dateReservation(entity.getDateReservation())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .prixEstime(entity.getPrixEstime())
                .build();
    }
}