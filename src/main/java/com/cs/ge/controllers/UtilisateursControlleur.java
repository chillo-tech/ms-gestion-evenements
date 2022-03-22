package com.cs.ge.controllers;

import com.cs.ge.entites.Utilisateur;
import com.cs.ge.services.UtilisateursService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "utilisateurs", produces = "application/json")

public class UtilisateursControlleur {

    private final UtilisateursService utilisateursService;

    public UtilisateursControlleur(UtilisateursService utilisateursService) {
        this.utilisateursService = utilisateursService;
    }

    @PostMapping
    public void creation(@RequestBody Utilisateur utilisateur) {
        utilisateursService.add(utilisateur);
    }

    @ResponseBody
    @GetMapping
    public List<Utilisateur> search() {
        return utilisateursService.search();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUtilisateur(@PathVariable String id) {
        utilisateursService.deleteUtilisateur(id);
    }

    @ResponseBody
    @PutMapping(value = "/{id}")
    public void updateUtilisateur(@PathVariable String id, @RequestBody Utilisateur utilisateur) {
        utilisateursService.updateUtilisateur(id, utilisateur);

    }

    @GetMapping("/queryparam")
    List<Utilisateur> search(@RequestParam("email") String email) {
        return utilisateursService.search();
    }


}
