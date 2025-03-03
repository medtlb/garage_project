package iscae.master.sb.voiture.controllers;

import iscae.master.sb.voiture.dtos.VoitureDto;
import iscae.master.sb.voiture.services.VoitureService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/voiture")
public class VoitureController {

    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @GetMapping
    public List<VoitureDto> getAll() {
        return voitureService.getAll();
    }

    @GetMapping("/user")
    public List<VoitureDto> getCurrentUserVoitures() {
        return voitureService.getCurrentUserVoitures();
    }

    @GetMapping("/{id}")
    public VoitureDto getById(@PathVariable("id") Long id) {
        return voitureService.getById(id);
    }

    @PostMapping
    public Long add(@RequestBody VoitureDto voitureDto) {
        return voitureService.addForCurrentUser(voitureDto);
    }

    @PutMapping("/{id}")
    public Long update(@RequestBody VoitureDto voitureDto, @PathVariable("id") Long id) {
        return voitureService.update(voitureDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        voitureService.deleteById(id);
    }
}