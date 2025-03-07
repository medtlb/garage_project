package iscae.master.sb.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "garage", schema = "public", catalog = "activite1")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GarageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom", length = 100, nullable = false)
    private String nom;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "telephone", length = 20)
    private String telephone;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "categorie", length = 50, nullable = true)
    @Enumerated(EnumType.STRING)
    private VehiculeCategorie categorie;

    @Column(name = "capacite")
    private Integer capacite;

    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisponibiliteEntity> disponibilites = new ArrayList<>();
}