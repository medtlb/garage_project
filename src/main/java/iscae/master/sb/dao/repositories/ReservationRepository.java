package iscae.master.sb.dao.repositories;

import iscae.master.sb.dao.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findByUtilisateurId(Long userId);
    List<ReservationEntity> findByGarageId(Long garageId);
    List<ReservationEntity> findByVoitureId(Long voitureId);
    List<ReservationEntity> findByStatus(String status);
}