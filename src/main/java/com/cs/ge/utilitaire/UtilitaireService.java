package com.cs.ge.utilitaire;

import com.cs.ge.exception.ApplicationException;
import org.springframework.stereotype.Service;

@Service
public class UtilitaireService {

    public void validationChaine(String chaine) {
        if (chaine == null || chaine.trim().isEmpty()) {
            throw new ApplicationException("Champs obligatoire");
        }
    }
}
