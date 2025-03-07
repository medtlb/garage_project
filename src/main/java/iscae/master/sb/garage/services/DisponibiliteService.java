package iscae.master.sb.garage.services;

import iscae.master.sb.dao.entities.DisponibiliteEntity;
import iscae.master.sb.dao.entities.GarageEntity;
import iscae.master.sb.dao.repositories.DisponibiliteRepository;
import iscae.master.sb.dao.repositories.GarageRepository;
import iscae.master.sb.garage.dtos.DisponibiliteDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisponibiliteService {

    private final DisponibiliteRepository disponibiliteRepository;
    private final GarageRepository garageRepository;

    public DisponibiliteService(DisponibiliteRepository disponibiliteRepository, GarageRepository garageRepository) {
        this.disponibiliteRepository = disponibiliteRepository;
        this.garageRepository = garageRepository;
    }

    public List<DisponibiliteDto> getAllByGarageId(Long garageId) {
        return disponibiliteRepository.findByGarageId(garageId).stream()
                .map(DisponibiliteDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DisponibiliteDto getById(Long id) {
        return disponibiliteRepository.findById(id)
                .map(DisponibiliteDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Disponibilite with ID " + id + " not found"));
    }

    @Transactional
    public Long add(DisponibiliteDto disponibiliteDto) {
        GarageEntity garage = garageRepository.findById(disponibiliteDto.getGarageId())
                .orElseThrow(() -> new RuntimeException("Garage with ID " + disponibiliteDto.getGarageId() + " not found"));

        DisponibiliteEntity entity = disponibiliteDto.toEntity();
        entity.setGarage(garage);

        return disponibiliteRepository.save(entity).getId();
    }

    @Transactional
    public Long update(DisponibiliteDto disponibiliteDto, Long id) {
        DisponibiliteEntity entity = disponibiliteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disponibilite with ID " + id + " not found"));

        entity.setJour(disponibiliteDto.getJour());
        entity.setHeureDebut(disponibiliteDto.getHeureDebut());
        entity.setHeureFin(disponibiliteDto.getHeureFin());

        return disponibiliteRepository.saveAndFlush(entity).getId();
    }

    @Transactional
    public void deleteById(Long id) {
        disponibiliteRepository.deleteById(id);
    }
}