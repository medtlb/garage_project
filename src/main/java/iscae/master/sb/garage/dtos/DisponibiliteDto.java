package iscae.master.sb.garage.dtos;

import iscae.master.sb.dao.entities.DisponibiliteEntity;
import iscae.master.sb.dao.entities.GarageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisponibiliteDto {
    private Long id;
    private Long garageId;
    private DayOfWeek jour;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    public DisponibiliteEntity toEntity() {
        return DisponibiliteEntity.builder()
                .id(id)
                .garage(GarageEntity.builder().id(garageId).build())
                .jour(jour)
                .heureDebut(heureDebut)
                .heureFin(heureFin)
                .build();
    }

    public static DisponibiliteDto fromEntity(DisponibiliteEntity entity) {
        return DisponibiliteDto.builder()
                .id(entity.getId())
                .garageId(entity.getGarage().getId())
                .jour(entity.getJour())
                .heureDebut(entity.getHeureDebut())
                .heureFin(entity.getHeureFin())
                .build();
    }
}