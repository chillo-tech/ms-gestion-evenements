package com.cs.ge.controllers;

import com.cs.ge.configuration.JwtTokenUtil;
import com.cs.ge.entites.JwtRequest;
import com.cs.ge.entites.JwtResponse;
import com.cs.ge.entites.Utilisateur;
import com.cs.ge.services.UtilisateursService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;


@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping(consumes = "application/json", produces = "application/json")
public class CompteUtilisateurControlleur {

    private final UtilisateursService utilisateursService;
    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody final JwtRequest authenticationRequest) throws Exception {
        this.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = this.utilisateursService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = this.jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(final String username, final String password) throws Exception {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (final DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (final BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping(path = "inscription")
    public void inscription(@RequestBody final Utilisateur utilisateur) throws MessagingException, IOException {
        System.out.println("firstName" + utilisateur.getFirstName());
        this.utilisateursService.inscription(utilisateur);
    }

    @ResponseBody
    @GetMapping(path = "/activate")
    public void activated(@RequestParam("code") final String code) {
        this.utilisateursService.activate(code);
    }
}



