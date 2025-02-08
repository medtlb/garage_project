package iscae.master.sb.reservation.services;

import iscae.master.sb.dao.entities.ReservationEntity;
import iscae.master.sb.dao.repositories.ReservationRepository;
import iscae.master.sb.reservation.dtos.ReservationDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationDto> getAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ReservationDto> getByUserId(Long userId) {
        return reservationRepository.findByUtilisateurId(userId).stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ReservationDto getById(Long id) {
        return reservationRepository.findById(id)
                .map(ReservationDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Reservation with ID " + id + " not found"));
    }

    @Transactional
    public Long add(ReservationDto reservationDto) {
        ReservationEntity entity = reservationDto.toEntity();
        if (entity.getStatus() == null) {
            entity.setStatus("en cours");
        }
        return reservationRepository.save(entity).getId();
    }

    @Transactional
    public Long update(ReservationDto reservationDto, Long id) {
        ReservationEntity entity = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation with ID " + id + " not found"));

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