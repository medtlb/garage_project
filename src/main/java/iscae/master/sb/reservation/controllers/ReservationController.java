package iscae.master.sb.reservation.controllers;

import iscae.master.sb.reservation.dtos.ReservationDto;
import iscae.master.sb.reservation.services.ReservationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ReservationDto> getAll() {
        return reservationService.getAll();
    }

    @GetMapping("/user/{userId}")
    public List<ReservationDto> getByUserId(@PathVariable("userId") Long userId) {
        return reservationService.getByUserId(userId);
    }

    @GetMapping("/{id}")
    public ReservationDto getById(@PathVariable("id") Long id) {
        return reservationService.getById(id);
    }

    @PostMapping
    public Long add(@RequestBody ReservationDto reservationDto) {
        return reservationService.add(reservationDto);
    }

    @PutMapping("/{id}")
    public Long update(@RequestBody ReservationDto reservationDto, @PathVariable("id") Long id) {
        return reservationService.update(reservationDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        reservationService.deleteById(id);
    }
}