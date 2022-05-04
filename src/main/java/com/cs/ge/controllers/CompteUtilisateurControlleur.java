package com.cs.ge.controllers;

import com.cs.ge.entites.Connexion;
import com.cs.ge.entites.Utilisateur;
import com.cs.ge.services.UtilisateursService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(consumes = "application/json", produces = "application/json")
public class CompteUtilisateurControlleur {
    private UtilisateursService utilisateursService;


    @PostMapping(path = "inscription")
    public void inscription(@RequestBody final Utilisateur utilisateur) throws MessagingException, IOException {
        System.out.println("firstName" + utilisateur.getFirstName());
        this.utilisateursService.inscription(utilisateur);
    }

    //  private String getSiteURL(HttpServletRequest request) {
    //    String siteURL = request.getRequestURL().toString();
    //     return siteURL.replace(request.getServletPath(), "");
    //x  }

    // return utilisateursService.search();
    // }

    //@GetMapping(path= "inscription/queryparam")
    //List<Utilisateur> search(@RequestParam("number") String number) {
    // return utilisateursService.search();
    // }


    @PostMapping(path = "connexion")
    public void connexion(final Connexion connexion) {
        this.utilisateursService.connexion(connexion);
    }

    @ResponseBody
    @GetMapping(path = "/activate")
    public void activated(@RequestParam("code") final String code) {
        this.utilisateursService.activate(code);
    }
}


