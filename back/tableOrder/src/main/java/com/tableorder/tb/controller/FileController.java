package com.tableorder.tb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Value("${upload.path}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println("Resolved upload directory: " + uploadDir); // Debug print

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("Created directory: " + uploadPath.toAbsolutePath().toString()); // Debug print
            }

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + extension;
            Path filePath = uploadPath.resolve(newFilename); // Corrected path construction
            System.out.println("Attempting to save file to: " + filePath.toAbsolutePath().toString()); // Debug print

            Files.copy(file.getInputStream(), filePath);
            System.out.println("File saved successfully to: " + filePath.toAbsolutePath().toString()); // Debug print

            // Return the relative path that can be accessed via HTTP (e.g., /images/filename.ext)
            return ResponseEntity.ok("/images/" + newFilename);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFile(@RequestParam("filePath") String filePath) {
        try {
            String relativePath = filePath.replaceFirst("/images/", "");
            Path fileToDeletePath = Paths.get(uploadDir).resolve(relativePath);

            if (Files.exists(fileToDeletePath) && Files.isRegularFile(fileToDeletePath)) {
                Files.delete(fileToDeletePath);
                System.out.println("File deleted successfully: " + fileToDeletePath.toAbsolutePath().toString());
                return ResponseEntity.noContent().build();
            } else {
                System.out.println("File not found or is not a regular file: " + fileToDeletePath.toAbsolutePath().toString());
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}