package com.example.ecommercebackend.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class ImageController {

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws MalformedURLException {
        // Search both product and user upload directories
        Path[] roots = new Path[] {
                Paths.get("src/main/resources/uploads/products/images/"),
                Paths.get("src/main/resources/uploads/users/avatars/")
        };
        for (Path root : roots) {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                String contentType = null;
                try {
                    contentType = java.nio.file.Files.probeContentType(file);
                } catch (Exception ignored) {}
                if (contentType == null) {
                    // Fallback guess by extension
                    String lower = filename.toLowerCase();
                    if (lower.endsWith(".png")) contentType = MediaType.IMAGE_PNG_VALUE;
                    else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) contentType = MediaType.IMAGE_JPEG_VALUE;
                    else if (lower.endsWith(".gif")) contentType = MediaType.IMAGE_GIF_VALUE;
                    else contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
                }
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + resource.getFilename())
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            }
        }
        return ResponseEntity.notFound().build();
    }
}


