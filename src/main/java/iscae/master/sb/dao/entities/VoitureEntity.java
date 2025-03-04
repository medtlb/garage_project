package iscae.master.sb.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "voiture", schema = "public", catalog = "activite1")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoitureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UtilisateurEntity utilisateur;

    @Column(name = "immatriculation", length = 20, nullable = false, unique = true)
    private String immatriculation;

    @Column(name = "marque", length = 50, nullable = false)
    private String marque;

    @Column(name = "model", length = 50)
    private String model;

    @Column(name = "image", length = 255)
    private String image;
}