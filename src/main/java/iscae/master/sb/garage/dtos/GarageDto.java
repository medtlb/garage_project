package iscae.master.sb.garage.dtos;

import iscae.master.sb.dao.entities.GarageEntity;
import iscae.master.sb.dao.entities.VehiculeCategorie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GarageDto {
    private Long id;
    private String nom;
    private Double latitude;
    private Double longitude;
    private String telephone;
    private String email;
    private Integer capacite;
    private VehiculeCategorie categorie;
    private List<DisponibiliteDto> disponibilites;

    public GarageEntity toEntity() {
        GarageEntity garage = GarageEntity.builder()
                .id(id)
                .nom(nom)
                .latitude(latitude)
                .longitude(longitude)
                .telephone(telephone)
                .email(email)
                .capacite(capacite)
                .categorie(categorie)
                .build();

        return garage;
    }

    public static GarageDto fromEntity(GarageEntity garageEntity) {
        GarageDto dto = GarageDto.builder()
                .id(garageEntity.getId())
                .nom(garageEntity.getNom())
                .latitude(garageEntity.getLatitude())
                .longitude(garageEntity.getLongitude())
                .telephone(garageEntity.getTelephone())
                .email(garageEntity.getEmail())
                .capacite(garageEntity.getCapacite())
                .categorie(garageEntity.getCategorie())
                .build();

        if (garageEntity.getDisponibilites() != null) {
            dto.setDisponibilites(garageEntity.getDisponibilites().stream()
                    .map(DisponibiliteDto::fromEntity)
                    .collect(Collectors.toList()));
        } else {
            dto.setDisponibilites(new ArrayList<>());
        }

        return dto;
    }
}