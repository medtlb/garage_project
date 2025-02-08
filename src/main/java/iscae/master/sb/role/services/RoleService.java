package iscae.master.sb.role.services;

import iscae.master.sb.dao.entities.RoleEntity;
import iscae.master.sb.dao.repositories.RoleRepository;
import iscae.master.sb.role.dtos.RoleDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDto> getAll() {
        return roleRepository.findAll().stream()
                .map(RoleDto::fromEntity)
                .collect(Collectors.toList());
    }

    public RoleDto getById(Short id) {
        return roleRepository.findById(id)
                .map(RoleDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Role with ID " + id + " not found"));
    }

    @Transactional
    public Short add(RoleDto roleDto) {
        RoleEntity roleEntity = roleDto.toEntity();
        return roleRepository.save(roleEntity).getId();
    }

    @Transactional
    public Short update(RoleDto roleDto, Short id) {
        RoleEntity roleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role with ID " + id + " not found"));

        roleEntity.setNom(roleDto.getNom());

        return roleRepository.saveAndFlush(roleEntity).getId();
    }

    @Transactional
    public void deleteById(Short id) {
        roleRepository.deleteById(id);
    }
}