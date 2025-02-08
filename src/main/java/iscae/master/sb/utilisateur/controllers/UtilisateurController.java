package iscae.master.sb.utilisateur.controllers;

import iscae.master.sb.utilisateur.dtos.UtilisateurDto;
import iscae.master.sb.utilisateur.services.UtilisateurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/utilisateur")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public List<UtilisateurDto> getAll() {
        return utilisateurService.getAll();
    }

    @GetMapping("/{id}")
    public UtilisateurDto getById(@PathVariable("id") Long id) {
        return utilisateurService.getById(id);
    }

    @PostMapping
    public Long add(@RequestBody UtilisateurDto utilisateurDto) {
        return utilisateurService.add(utilisateurDto);
    }

    @PutMapping("/{id}")
    public Long update(@RequestBody UtilisateurDto utilisateurDto, @PathVariable("id") Long id) {
        return utilisateurService.update(utilisateurDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        utilisateurService.deleteById(id);
    }
}