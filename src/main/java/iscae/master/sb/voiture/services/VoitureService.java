package iscae.master.sb.voiture.services;

import iscae.master.sb.dao.entities.VoitureEntity;
import iscae.master.sb.dao.repositories.VoitureRepository;
import iscae.master.sb.voiture.dtos.VoitureDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoitureService {

    private final VoitureRepository voitureRepository;

    public VoitureService(VoitureRepository voitureRepository) {
        this.voitureRepository = voitureRepository;
    }

    public List<VoitureDto> getAll() {
        return voitureRepository.findAll().stream()
                .map(VoitureDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<VoitureDto> getByUserId(Long userId) {
        return voitureRepository.findByUtilisateurId(userId).stream()
                .map(VoitureDto::fromEntity)
                .collect(Collectors.toList());
    }

    public VoitureDto getById(Long id) {
        return voitureRepository.findById(id)
                .map(VoitureDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Voiture with ID " + id + " not found"));
    }

    @Transactional
    public Long add(VoitureDto voitureDto) {
        if (voitureRepository.existsByImmatriculation(voitureDto.getImmatriculation())) {
            throw new RuntimeException("Immatriculation already exists");
        }

        VoitureEntity voitureEntity = voitureDto.toEntity();
        return voitureRepository.save(voitureEntity).getId();
    }

    @Transactional
    public Long update(VoitureDto voitureDto, Long id) {
        VoitureEntity voitureEntity = voitureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voiture with ID " + id + " not found"));

        // Check if immatriculation is being changed and if new one already exists
        if (!voitureEntity.getImmatriculation().equals(voitureDto.getImmatriculation()) &&
                voitureRepository.existsByImmatriculation(voitureDto.getImmatriculation())) {
            throw new RuntimeException("Immatriculation already exists");
        }

        voitureEntity.setImmatriculation(voitureDto.getImmatriculation());
        voitureEntity.setMarque(voitureDto.getMarque());
        voitureEntity.setModel(voitureDto.getModel());

        return voitureRepository.saveAndFlush(voitureEntity).getId();
    }

    @Transactional
    public void deleteById(Long id) {
        voitureRepository.deleteById(id);
    }
}