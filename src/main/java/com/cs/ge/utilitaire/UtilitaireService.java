package com.cs.ge.utilitaire;

import com.cs.ge.exception.ApplicationException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

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

    public static boolean valEmail(final String username) {
        final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        final Pattern pat = Pattern.compile(emailRegex);
        if (username == null) {
            return false;
        }

        final boolean resultat = pat.matcher(username).matches();
        return resultat;
    }

    public static boolean valNumber(final String username) {
        final String numberRegex = "(6|5|0|9)?[0-9]{9}";
        final Pattern pat = Pattern.compile(numberRegex);
        if (username == null) {
            return false;
        }
        final boolean resultat = pat.matcher(username).matches();
        return resultat;
    }

}
