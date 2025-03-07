package iscae.master.sb.dao.repositories;

import iscae.master.sb.dao.entities.DisponibiliteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DisponibiliteRepository extends JpaRepository<DisponibiliteEntity, Long> {
    List<DisponibiliteEntity> findByGarageId(Long garageId);
}