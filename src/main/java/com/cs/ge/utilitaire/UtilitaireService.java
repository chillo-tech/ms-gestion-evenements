package com.cs.ge.utilitaire;

import com.cs.ge.exception.ApplicationException;
import org.springframework.stereotype.Service;

@Service
public class UtilitaireService {
    // private final UtilisateurRepository utilisateurRepository;

    //public UtilitaireService(final UtilisateurRepository utilisateurRepository) {
    //     this.utilisateurRepository = utilisateurRepository;
    //   }


    public static void validationChaine(final String chaine) {
        if (chaine == null || chaine.trim().isEmpty()) {
            throw new ApplicationException("Champs obligatoire");
        }
    }
}
