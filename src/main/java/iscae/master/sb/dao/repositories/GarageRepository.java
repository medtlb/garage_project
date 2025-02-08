package iscae.master.sb.dao.repositories;

import iscae.master.sb.dao.entities.GarageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageRepository extends JpaRepository<GarageEntity, Long> {
}