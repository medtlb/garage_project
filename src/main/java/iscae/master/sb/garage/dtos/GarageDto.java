package iscae.master.sb.garage.dtos;

import iscae.master.sb.dao.entities.GarageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GarageDto {
    private Long id;
    private String nom;
    // Removed address
    private Double latitude;
    private Double longitude;
    private String telephone;
    private String email;
    private Integer capacite;

    public GarageEntity toEntity() {
        return GarageEntity.builder()
                .id(id)
                .nom(nom)
                // Removed address
                .latitude(latitude)
                .longitude(longitude)
                .telephone(telephone)
                .email(email)
                .capacite(capacite)
                .build();
    }

    public static GarageDto fromEntity(GarageEntity garageEntity) {
        return GarageDto.builder()
                .id(garageEntity.getId())
                .nom(garageEntity.getNom())
                // Removed address
                .latitude(garageEntity.getLatitude())
                .longitude(garageEntity.getLongitude())
                .telephone(garageEntity.getTelephone())
                .email(garageEntity.getEmail())
                .capacite(garageEntity.getCapacite())
                .build();
    }
}