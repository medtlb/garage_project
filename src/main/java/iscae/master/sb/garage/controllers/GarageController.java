package iscae.master.sb.garage.controllers;

import iscae.master.sb.garage.dtos.GarageDto;
import iscae.master.sb.garage.services.GarageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/garage")
public class GarageController {

    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @GetMapping
    public List<GarageDto> getAll() {
        return garageService.getAll();
    }

    @GetMapping("/{id}")
    public GarageDto getById(@PathVariable("id") Long id) {
        return garageService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Long add(@RequestBody GarageDto garageDto) {
        return garageService.add(garageDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Long update(@RequestBody GarageDto garageDto, @PathVariable("id") Long id) {
        return garageService.update(garageDto, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable("id") Long id) {
        garageService.deleteById(id);
    }
}