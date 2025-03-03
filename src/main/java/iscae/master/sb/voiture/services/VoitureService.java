package iscae.master.sb.voiture.services;

import iscae.master.sb.dao.entities.UtilisateurEntity;
import iscae.master.sb.dao.entities.VoitureEntity;
import iscae.master.sb.dao.repositories.UtilisateurRepository;
import iscae.master.sb.dao.repositories.VoitureRepository;
import iscae.master.sb.voiture.dtos.VoitureDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoitureService {

    private final VoitureRepository voitureRepository;
    private final UtilisateurRepository utilisateurRepository;

    public VoitureService(VoitureRepository voitureRepository,
                          UtilisateurRepository utilisateurRepository) {
        this.voitureRepository = voitureRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<VoitureDto> getAll() {
        return voitureRepository.findAll().stream()
                .map(VoitureDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<VoitureDto> getCurrentUserVoitures() {
        // Get the current authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Find the user by email
        UtilisateurEntity currentUser = utilisateurRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find vehicles for the current user
        return voitureRepository.findByUtilisateurId(currentUser.getId()).stream()
                .map(VoitureDto::fromEntity)
                .collect(Collectors.toList());
    }

    public VoitureDto getById(Long id) {
        return voitureRepository.findById(id)
                .map(VoitureDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Voiture with ID " + id + " not found"));
    }

    @Transactional
    public Long addForCurrentUser(VoitureDto voitureDto) {
        // Get the current authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Find the user by email
        UtilisateurEntity currentUser = utilisateurRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if immatriculation already exists
        if (voitureRepository.existsByImmatriculation(voitureDto.getImmatriculation())) {
            throw new RuntimeException("Immatriculation already exists");
        }

        // Create voiture entity
        VoitureEntity voitureEntity = voitureDto.toEntity();

        // Set the current user as the utilisateur
        voitureEntity.setUtilisateur(currentUser);

        // Save the vehicle
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