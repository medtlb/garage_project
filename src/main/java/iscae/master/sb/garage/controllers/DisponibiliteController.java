package iscae.master.sb.garage.controllers;

import iscae.master.sb.garage.dtos.DisponibiliteDto;
import iscae.master.sb.garage.services.DisponibiliteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/disponibilite")
public class DisponibiliteController {

    private final DisponibiliteService disponibiliteService;

    public DisponibiliteController(DisponibiliteService disponibiliteService) {
        this.disponibiliteService = disponibiliteService;
    }

    @GetMapping("/garage/{garageId}")
    public List<DisponibiliteDto> getAllByGarageId(@PathVariable("garageId") Long garageId) {
        return disponibiliteService.getAllByGarageId(garageId);
    }

    @GetMapping("/{id}")
    public DisponibiliteDto getById(@PathVariable("id") Long id) {
        return disponibiliteService.getById(id);
    }

    @PostMapping
    public Long add(@RequestBody DisponibiliteDto disponibiliteDto) {
        return disponibiliteService.add(disponibiliteDto);
    }

    @PutMapping("/{id}")
    public Long update(@RequestBody DisponibiliteDto disponibiliteDto, @PathVariable("id") Long id) {
        return disponibiliteService.update(disponibiliteDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        disponibiliteService.deleteById(id);
    }
}