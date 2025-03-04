package iscae.master.sb.voiture.services;

import iscae.master.sb.dao.entities.UtilisateurEntity;
import iscae.master.sb.dao.entities.VoitureEntity;
import iscae.master.sb.dao.repositories.UtilisateurRepository;
import iscae.master.sb.dao.repositories.VoitureRepository;
import iscae.master.sb.utils.FileStorageService;
import iscae.master.sb.voiture.dtos.VoitureDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoitureService {

    private final VoitureRepository voitureRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final FileStorageService fileStorageService;

    public VoitureService(VoitureRepository voitureRepository,
                          UtilisateurRepository utilisateurRepository,
                          FileStorageService fileStorageService) {
        this.voitureRepository = voitureRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.fileStorageService = fileStorageService;
        System.out.println("VoitureService created with fileStorageService: " + (fileStorageService != null ? "yes" : "no"));
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
        System.out.println("Getting cars for user: " + currentUsername);

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
        System.out.println("Adding car for user: " + currentUsername);

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
        VoitureEntity savedEntity = voitureRepository.save(voitureEntity);
        System.out.println("Car saved with ID: " + savedEntity.getId());
        return savedEntity.getId();
    }

    @Transactional
    public Long addForCurrentUserWithImage(VoitureDto voitureDto, MultipartFile file) {
        // Get the current authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        System.out.println("Adding car with image for user: " + currentUsername);

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

        // Handle the image file if provided
        System.out.println("File in service: " + (file != null ? "yes, not empty: " + !file.isEmpty() : "no"));
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = fileStorageService.storeFile(file);
                System.out.println("File saved with name: " + fileName);
                voitureEntity.setImage(fileName);
            } catch (Exception e) {
                System.err.println("Error saving file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No valid file to save");
        }

        // Save the vehicle
        VoitureEntity savedEntity = voitureRepository.save(voitureEntity);
        System.out.println("Car saved with ID: " + savedEntity.getId());
        System.out.println("Saved entity image value: " + savedEntity.getImage());
        return savedEntity.getId();
    }

    @Transactional
    public Long update(VoitureDto voitureDto, Long id) {
        System.out.println("Updating car with ID: " + id);
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
        voitureEntity.setImage(voitureDto.getImage());

        VoitureEntity updatedEntity = voitureRepository.saveAndFlush(voitureEntity);
        System.out.println("Car updated, image value: " + updatedEntity.getImage());
        return updatedEntity.getId();
    }

    @Transactional
    public Long updateWithImage(VoitureDto voitureDto, Long id, MultipartFile file) {
        System.out.println("Updating car with ID: " + id + " with new image");
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

        // Handle the image file if provided
        System.out.println("File in update service: " + (file != null ? "yes, not empty: " + !file.isEmpty() : "no"));
        if (file != null && !file.isEmpty()) {
            try {
                // Delete old image if exists
                if (voitureEntity.getImage() != null && !voitureEntity.getImage().isEmpty()) {
                    System.out.println("Deleting old image: " + voitureEntity.getImage());
                    fileStorageService.deleteFile(voitureEntity.getImage());
                }

                String fileName = fileStorageService.storeFile(file);
                System.out.println("New file saved with name: " + fileName);
                voitureEntity.setImage(fileName);
            } catch (Exception e) {
                System.err.println("Error handling file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No valid file to update");
        }

        VoitureEntity updatedEntity = voitureRepository.saveAndFlush(voitureEntity);
        System.out.println("Car updated with ID: " + updatedEntity.getId());
        System.out.println("Updated entity image value: " + updatedEntity.getImage());
        return updatedEntity.getId();
    }

    @Transactional
    public void deleteById(Long id) {
        System.out.println("Deleting car with ID: " + id);
        VoitureEntity voiture = voitureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voiture with ID " + id + " not found"));

        // Delete the image file if it exists
        if (voiture.getImage() != null && !voiture.getImage().isEmpty()) {
            System.out.println("Deleting image: " + voiture.getImage());
            fileStorageService.deleteFile(voiture.getImage());
        }

        voitureRepository.deleteById(id);
        System.out.println("Car deleted");
    }

    @Transactional
    public String storeImage(Long voitureId, MultipartFile file) {
        System.out.println("Storing image for car with ID: " + voitureId);
        VoitureEntity voitureEntity = voitureRepository.findById(voitureId)
                .orElseThrow(() -> new RuntimeException("Voiture with ID " + voitureId + " not found"));

        // Remove old image file if exists
        if (voitureEntity.getImage() != null && !voitureEntity.getImage().isEmpty()) {
            System.out.println("Removing old image: " + voitureEntity.getImage());
            fileStorageService.deleteFile(voitureEntity.getImage());
        }

        // Store the new file
        System.out.println("Storing new file");
        String fileName = fileStorageService.storeFile(file);
        System.out.println("File stored with name: " + fileName);

        // Update the voiture entity with the new image filename
        voitureEntity.setImage(fileName);
        voitureRepository.save(voitureEntity);
        System.out.println("Car updated with new image");

        // Return the full URL to access the image
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/voiture/images/")
                .path(fileName)
                .toUriString();
        System.out.println("Image URL: " + imageUrl);
        return imageUrl;
    }
}