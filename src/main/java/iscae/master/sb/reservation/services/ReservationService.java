package iscae.master.sb.reservation.services;

import iscae.master.sb.dao.entities.DisponibiliteEntity;
import iscae.master.sb.dao.entities.ReservationEntity;
import iscae.master.sb.dao.entities.UtilisateurEntity;
import iscae.master.sb.dao.repositories.DisponibiliteRepository;
import iscae.master.sb.dao.repositories.ReservationRepository;
import iscae.master.sb.dao.repositories.UtilisateurRepository;
import iscae.master.sb.reservation.dtos.ReservationDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final DisponibiliteRepository disponibiliteRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              UtilisateurRepository utilisateurRepository,
                              DisponibiliteRepository disponibiliteRepository) {
        this.reservationRepository = reservationRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.disponibiliteRepository = disponibiliteRepository;
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

    private boolean isGarageAvailable(Long garageId, LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        LocalTime time = dateTime.toLocalTime();

        // Find matching availability for this garage and day of week
        List<DisponibiliteEntity> disponibilites = disponibiliteRepository.findByGarageId(garageId).stream()
                .filter(d -> d.getJour() == dayOfWeek)
                .filter(d -> !time.isBefore(d.getHeureDebut()) && !time.isAfter(d.getHeureFin()))
                .collect(Collectors.toList());

        return !disponibilites.isEmpty();
    }

    @Transactional
    public Long addForCurrentUser(ReservationDto reservationDto) {
        // Get the current authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Find the user by email
        UtilisateurEntity currentUser = utilisateurRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the garage is available at the requested time
        if (!isGarageAvailable(reservationDto.getGarageId(), reservationDto.getDateReservation())) {
            throw new RuntimeException("Garage is not available at the requested time");
        }

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

        // If date/time changed, check availability
        if (reservationDto.getDateReservation() != null &&
                !reservationDto.getDateReservation().equals(entity.getDateReservation())) {

            if (!isGarageAvailable(entity.getGarage().getId(), reservationDto.getDateReservation())) {
                throw new RuntimeException("Garage is not available at the requested time");
            }

            entity.setDateReservation(reservationDto.getDateReservation());
        }

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