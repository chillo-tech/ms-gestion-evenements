package com.cs.ge.services;

import com.cs.ge.entites.JwtRequest;
import com.cs.ge.entites.Mail;
import com.cs.ge.entites.Utilisateur;
import com.cs.ge.entites.Verification;
import com.cs.ge.exception.ApplicationException;
import com.cs.ge.repositories.UtilisateurRepository;
import com.cs.ge.utilitaire.UtilitaireService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UtilisateursService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private final VerificationService verificationService;
    private final UtilitaireService utilitaireService;
    private final PasswordEncoder passwordEncoder;

    public UtilisateursService(final UtilisateurRepository utilisateurRepository, final UtilitaireService utilitaireService, final VerificationService verificationService) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilitaireService = utilitaireService;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.verificationService = verificationService;
    }


    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final JwtRequest jwtRequest = new JwtRequest();
        if (jwtRequest.getUsername().equals(username)) {
            return this.utilisateurRepository.findByUsername(username);

        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }


    public void activate(final String code) {
        final Verification verification = this.verificationService.getByCode(code);
        Utilisateur utilisateur = verification.getUtilisateur();
        utilisateur = this.utilisateurRepository.findById(utilisateur.getId()).orElseThrow(() -> new ApplicationException("aucun utilisateur pour ce code"));
        ;
        utilisateur.setEnabled(true);
        final LocalDateTime localDateTime = verification.getDateExpiration();
        //if (localDateTime=)
        this.utilisateurRepository.save(utilisateur);
    }

    public void validationUsername(final String username) {
        final Optional<Utilisateur> exist = Optional.ofNullable(this.utilisateurRepository.findByUsername(username));
        if (exist.isPresent()) {
            throw new ApplicationException("Username existe déjà");
        }
    }

    public void add(final Utilisateur utilisateur) { // en entrée je dois avoir quelque chose sous la forme d'un Utilisateur de type utilisateur
        this.validationUsername(utilisateur.getUsername());
        String lastName = utilisateur.getLastName();
        lastName = lastName.toUpperCase();
        utilisateur.setLastName(lastName);
        this.utilisateurRepository.save(utilisateur);
    }

    public List<Utilisateur> search() {
        return this.utilisateurRepository.findAll();
    }

    public void deleteUtilisateur(final String id) {
        this.utilisateurRepository.deleteById(id);
    }

    public void updateUtilisateur(final String id, final Utilisateur utilisateur) {
        final Optional<Utilisateur> current = this.utilisateurRepository.findById(id);
        if (current.isPresent()) {
            final Utilisateur foundUser = current.get();
            foundUser.setId(id);
            foundUser.setFirstName(utilisateur.getFirstName());
            foundUser.setLastName(utilisateur.getLastName());
            foundUser.setUsername(utilisateur.getUsername());
            this.utilisateurRepository.save(foundUser);
        }
    }

    public List<Utilisateur> search(final String username) {
        return (List<Utilisateur>) this.utilisateurRepository.findByUsername(username);
    }

    public String inscription(final Utilisateur utilisateur) throws MessagingException, IOException {
        final String encodedPassword = this.passwordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(encodedPassword);
        UtilitaireService.validationChaine(utilisateur.getFirstName());
        UtilitaireService.valEmail(utilisateur.getUsername());
        UtilitaireService.valNumber(utilisateur.getUsername());
        this.utilisateurRepository.save(utilisateur);
        this.verificationService.createCode(utilisateur);
        final Mail mail = new Mail();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", utilisateur.getFirstName());
        model.put("location", "United States");
        model.put("sign", "Java Developer");
        mail.setMailTo(utilisateur.getUsername());
        mail.setSubject("mail de confirmation");
        mail.setMailFrom("noreply@athena.com");
        mail.setProps(model);
        this.verificationService.sendEmail(mail);
        return "success";
    }


}