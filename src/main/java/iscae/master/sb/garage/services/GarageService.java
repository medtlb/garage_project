package iscae.master.sb.garage.services;

import iscae.master.sb.dao.entities.GarageEntity;
import iscae.master.sb.dao.repositories.GarageRepository;
import iscae.master.sb.garage.dtos.GarageDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GarageService {

    private final GarageRepository garageRepository;

    public GarageService(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    public List<GarageDto> getAll() {
        return garageRepository.findAll().stream()
                .map(GarageDto::fromEntity)
                .collect(Collectors.toList());
    }

    public GarageDto getById(Long id) {
        return garageRepository.findById(id)
                .map(GarageDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Garage with ID " + id + " not found"));
    }

    @Transactional
    public Long add(GarageDto garageDto) {
        GarageEntity garageEntity = garageDto.toEntity();
        return garageRepository.save(garageEntity).getId();
    }

    @Transactional
    public Long update(GarageDto garageDto, Long id) {
        GarageEntity garageEntity = garageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Garage with ID " + id + " not found"));

        garageEntity.setNom(garageDto.getNom());
        garageEntity.setAddress(garageDto.getAddress());
        garageEntity.setTelephone(garageDto.getTelephone());
        garageEntity.setEmail(garageDto.getEmail());
        garageEntity.setCapacite(garageDto.getCapacite());

        return garageRepository.saveAndFlush(garageEntity).getId();
    }

    @Transactional
    public void deleteById(Long id) {
        garageRepository.deleteById(id);
    }
}