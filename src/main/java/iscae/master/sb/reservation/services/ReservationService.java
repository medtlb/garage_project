package iscae.master.sb.reservation.services;

import iscae.master.sb.dao.entities.ReservationEntity;
import iscae.master.sb.dao.entities.UtilisateurEntity;
import iscae.master.sb.dao.repositories.ReservationRepository;
import iscae.master.sb.dao.repositories.UtilisateurRepository;
import iscae.master.sb.reservation.dtos.ReservationDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              UtilisateurRepository utilisateurRepository) {
        this.reservationRepository = reservationRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<ReservationDto> getAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ReservationDto> getCurrentUserReservations() {
        // Get the current authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Find the user by email
        UtilisateurEntity currentUser = utilisateurRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find reservations for the current user
        return reservationRepository.findByUtilisateurId(currentUser.getId()).stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ReservationDto getById(Long id) {
        return reservationRepository.findById(id)
                .map(ReservationDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Reservation with ID " + id + " not found"));
    }

    @Transactional
    public Long addForCurrentUser(ReservationDto reservationDto) {
        // Get the current authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Find the user by email
        UtilisateurEntity currentUser = utilisateurRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create reservation entity
        ReservationEntity entity = reservationDto.toEntity();

        // Set the current user as the utilisateur
        entity.setUtilisateur(currentUser);

        // Set default status if not provided
        if (entity.getStatus() == null) {
            entity.setStatus("en cours");
        }

        // Save the reservation
        return reservationRepository.save(entity).getId();
    }

    @Transactional
    public Long update(ReservationDto reservationDto, Long id) {
        ReservationEntity entity = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation with ID " + id + " not found"));

        // Only update specific fields
        entity.setDescription(reservationDto.getDescription());
        entity.setStatus(reservationDto.getStatus());
        entity.setPrixEstime(reservationDto.getPrixEstime());

        return reservationRepository.saveAndFlush(entity).getId();
    }

    @Transactional
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}