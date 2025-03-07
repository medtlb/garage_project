package iscae.master.sb.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "disponibilite", schema = "public", catalog = "activite1")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisponibiliteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "garage_id", nullable = false)
    private GarageEntity garage;

    @Enumerated(EnumType.STRING)
    @Column(name = "jour", nullable = false)
    private DayOfWeek jour;

    @Column(name = "heure_debut", nullable = false)
    private LocalTime heureDebut;

    @Column(name = "heure_fin", nullable = false)
    private LocalTime heureFin;
}