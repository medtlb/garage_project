package iscae.master.sb.utilisateur.services;

import iscae.master.sb.dao.entities.UtilisateurEntity;
import iscae.master.sb.dao.repositories.UtilisateurRepository;
import iscae.master.sb.utilisateur.dtos.UtilisateurDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UtilisateurDto> getAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    public UtilisateurDto getById(Long id) {
        return utilisateurRepository.findById(id)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Utilisateur with ID " + id + " not found"));
    }

    @Transactional
    public Long add(UtilisateurDto utilisateurDto) {
        if (utilisateurRepository.existsByEmail(utilisateurDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        UtilisateurEntity utilisateurEntity = utilisateurDto.toEntity();
        // Encrypt password before saving
        utilisateurEntity.setPassword(passwordEncoder.encode(utilisateurEntity.getPassword()));
        return utilisateurRepository.save(utilisateurEntity).getId();
    }

    @Transactional
    public Long update(UtilisateurDto utilisateurDto, Long id) {
        UtilisateurEntity utilisateurEntity = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur with ID " + id + " not found"));

        // Check if email is being changed and if new email already exists
        if (!utilisateurEntity.getEmail().equals(utilisateurDto.getEmail()) &&
                utilisateurRepository.existsByEmail(utilisateurDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        utilisateurEntity.setNom(utilisateurDto.getNom());
        utilisateurEntity.setEmail(utilisateurDto.getEmail());
        if (utilisateurDto.getPassword() != null && !utilisateurDto.getPassword().isEmpty()) {
            utilisateurEntity.setPassword(passwordEncoder.encode(utilisateurDto.getPassword()));
        }

        return utilisateurRepository.saveAndFlush(utilisateurEntity).getId();
    }

    @Transactional
    public void deleteById(Long id) {
        utilisateurRepository.deleteById(id);
    }
}