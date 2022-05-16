package com.cs.ge.controllers;

import com.cs.ge.entites.Categorie;
import com.cs.ge.services.CategorieService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@RestController
@RequestMapping(path = "categorie", produces = "application/json")
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(final CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @PostMapping
    public void creation(@RequestBody final Categorie categorie) {
        this.categorieService.add(categorie);
    }

    @ResponseBody
    @GetMapping
    public List<Categorie> search() {
        return this.categorieService.search();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCategorie(@PathVariable final String id) {
        this.categorieService.deleteCategorie(id);
    }

    @ResponseBody
    @PutMapping(value = "/{id}")
    public void updateCategorie(@PathVariable final String id, @RequestBody final Categorie categorie) {
        this.categorieService.updateCategorie(id, categorie);
    }

    @GetMapping("/queryparam")
    List<Categorie> search(@RequestParam("id") final String id) {
        return this.categorieService.search();
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage() throws IOException {
        final File img = new File("src/main/resources/image/Mariage/alexis-antoine-GBpKfFfJhgw-unsplash.jpg");
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
    }
}


