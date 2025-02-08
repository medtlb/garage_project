package iscae.master.sb.dao.repositories;

import iscae.master.sb.dao.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Short> {
}