package com.cs.ge.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("image")
public class imgController {

    @GetMapping("showme")
    public ResponseEntity<byte[]> getImage() throws IOException {
        final File img = new File("src/main/resources/image/Mariage/alexis-antoine-GBpKfFfJhgw-unsplash.jpg");
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
    }
}
