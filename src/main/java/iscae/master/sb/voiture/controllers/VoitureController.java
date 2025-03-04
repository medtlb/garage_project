package iscae.master.sb.voiture.controllers;

import iscae.master.sb.voiture.dtos.VoitureDto;
import iscae.master.sb.voiture.services.VoitureService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("api/voiture")
public class VoitureController {

    private final VoitureService voitureService;
    private final String uploadDir;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
        this.uploadDir = "uploads";
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

    @PostMapping(value = "/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long addWithImage(
            @RequestParam("immatriculation") String immatriculation,
            @RequestParam("marque") String marque,
            @RequestParam("model") String model,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        System.out.println("Controller received request: imm=" + immatriculation + ", marque=" + marque + ", model=" + model);
        System.out.println("File received: " + (file != null ? "yes, size: " + file.getSize() + ", name: " + file.getOriginalFilename() : "no"));

        VoitureDto voitureDto = new VoitureDto();
        voitureDto.setImmatriculation(immatriculation);
        voitureDto.setMarque(marque);
        voitureDto.setModel(model);

        return voitureService.addForCurrentUserWithImage(voitureDto, file);
    }

    @PutMapping("/{id}")
    public Long update(@RequestBody VoitureDto voitureDto, @PathVariable("id") Long id) {
        return voitureService.update(voitureDto, id);
    }

    @PutMapping(value = "/{id}/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long updateWithImage(
            @PathVariable("id") Long id,
            @RequestParam("immatriculation") String immatriculation,
            @RequestParam("marque") String marque,
            @RequestParam("model") String model,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        System.out.println("Update with image: id=" + id);
        System.out.println("File received: " + (file != null ? "yes, size: " + file.getSize() : "no"));

        VoitureDto voitureDto = new VoitureDto();
        voitureDto.setImmatriculation(immatriculation);
        voitureDto.setMarque(marque);
        voitureDto.setModel(model);

        return voitureService.updateWithImage(voitureDto, id, file);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        voitureService.deleteById(id);
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable("id") Long id,
                                              @RequestParam("file") MultipartFile file) {
        System.out.println("Upload image for id=" + id);
        System.out.println("File received: " + (file != null ? "yes, size: " + file.getSize() : "no"));

        String imageUrl = voitureService.storeImage(id, file);
        return ResponseEntity.ok(imageUrl);
    }

    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            System.out.println("Accessing image: " + filePath.toAbsolutePath());

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                System.out.println("File not found: " + filePath.toAbsolutePath());
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            System.err.println("Error accessing file: " + ex.getMessage());
            ex.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}