package com.cs.ge.controllers;

import com.cs.ge.entites.Utilisateur;
import com.cs.ge.entites.Verification;
import com.cs.ge.repositories.UtilisateurRepository;
import com.cs.ge.services.UtilisateursService;
import com.cs.ge.services.VerificationService;
import com.sun.mail.imap.Utility;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    //@GetMapping(path ="confirm")
   // public  void confirm(@RequestParam("code")  String code){
        //utilisateursService.confirmCode(code);
   // }
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
    List<Utilisateur> search(@RequestParam("username") String username) {
        return utilisateursService.search();
    }
    }


