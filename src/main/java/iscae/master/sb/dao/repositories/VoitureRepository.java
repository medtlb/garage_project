package iscae.master.sb.dao.repositories;

import iscae.master.sb.dao.entities.VoitureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoitureRepository extends JpaRepository<VoitureEntity, Long> {
    List<VoitureEntity> findByUtilisateurId(Long userId);
    boolean existsByImmatriculation(String immatriculation);
}