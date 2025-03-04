package iscae.master.sb.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Created directory: " + path.toAbsolutePath());
            } else {
                System.out.println("Directory exists: " + path.toAbsolutePath());
            }
            System.out.println("Directory writable: " + Files.isWritable(path));
        } catch (IOException e) {
            System.err.println("Could not initialize storage: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    public String storeFile(MultipartFile file) {
        try {
            if (file == null) {
                System.err.println("File is null");
                throw new RuntimeException("Failed to store null file");
            }

            // Create the upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("Created directory: " + uploadPath.toAbsolutePath());
            }

            System.out.println("Storing file: " + file.getOriginalFilename() + ", size: " + file.getSize());

            // Generate a unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFilename = UUID.randomUUID().toString() + fileExtension;

            // Copy the file to the upload directory
            Path targetLocation = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File stored successfully: " + newFilename + " at " + targetLocation.toAbsolutePath());
            return newFilename;
        } catch (IOException ex) {
            System.err.println("Error storing file: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException("Could not store file. Error: " + ex.getMessage());
        }
    }

    public void deleteFile(String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            System.out.println("Deleting file: " + filePath.toAbsolutePath());
            if (Files.deleteIfExists(filePath)) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("File not found");
            }
        } catch (IOException ex) {
            System.err.println("Error deleting file: " + ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException("Could not delete file. Error: " + ex.getMessage());
        }
    }
}