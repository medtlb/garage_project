package iscae.master.sb.garage.dtos;

import iscae.master.sb.dao.entities.GarageEntity;
import iscae.master.sb.dao.entities.VehiculeCategorie;
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
    private VehiculeCategorie categorie;

// Update toEntity and fromEntity methods accordingly

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
                .categorie(categorie)
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
                .categorie(garageEntity.getCategorie())
                .build();
    }
}