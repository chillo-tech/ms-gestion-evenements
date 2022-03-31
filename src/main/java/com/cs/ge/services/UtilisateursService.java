package com.cs.ge.services;
import com.cs.ge.entites.Connexion;
import com.cs.ge.entites.Mail;
import com.cs.ge.entites.Verification;
import com.cs.ge.exception.ApplicationException;
import com.cs.ge.utilitaire.UtilitaireService;
import com.cs.ge.entites.Utilisateur;
import com.cs.ge.repositories.UtilisateurRepository;
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
import java.util.Map;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateursService implements UserDetailsService {

	private final UtilisateurRepository utilisateurRepository;
	private UtilitaireService utilitaireService;
	private PasswordEncoder passwordEncoder;
	private final VerificationService verificationService;


	public UtilisateursService(UtilisateurRepository utilisateurRepository, UtilitaireService utilitaireService, VerificationService verificationService) {
		this.utilisateurRepository = utilisateurRepository;
		this.utilitaireService = utilitaireService;
		this.passwordEncoder = new BCryptPasswordEncoder();
		this.verificationService = verificationService;
	}

	@Override
	public  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

	public static boolean valEmail(String username) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
				"[a-zA-Z0-9_+&*-]+)*@" +
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
				"A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		if (username == null)
			return false;

		boolean resultat = pat.matcher(username).matches();
		return resultat;
	}

	public static boolean valNumber(String username) {
		String numberRegex = "(6|5|0|9)?[0-9]{9}";
		Pattern pat = Pattern.compile(numberRegex);
		if (username == null)
			return false;
		boolean resultat = pat.matcher(username).matches();
		return resultat;
	}

	public void activate(String code) {
		Verification verification =verificationService.getByCode(code);
		Utilisateur utilisateur = verification.getUtilisateur();
		utilisateur = utilisateurRepository.findById(utilisateur.getId()).orElseThrow(() -> new ApplicationException("aucun utilisateur pour ce code"));;
		utilisateur.setEnabled(true);
		LocalDateTime localDateTime=verification.getDateExpiration();
		//if (localDateTime=)
		utilisateurRepository.save(utilisateur);
		}

	public void add(Utilisateur utilisateur) { // en entrée je dois avoir quelque chose sous la forme d'un Utilisateur de type utilisateur
		String lastName = utilisateur.getLastName();
		lastName = lastName.toUpperCase();
		utilisateur.setLastName(lastName);
		utilisateurRepository.save(utilisateur);
	}

	public List<Utilisateur> search() {
		return utilisateurRepository.findAll();
	}

	public void deleteUtilisateur(String id) {
		utilisateurRepository.deleteById(id);
	}

	public void updateUtilisateur(String id, Utilisateur utilisateur) {
		Optional<Utilisateur> current = this.utilisateurRepository.findById(id);
		  if (current.isPresent()) {
			  Utilisateur foundUser = current.get();
			foundUser.setId(id);
			foundUser.setFirstName(utilisateur.getFirstName());
			foundUser.setLastName(utilisateur.getLastName());
			foundUser.setUsername(utilisateur.getUsername());
			utilisateurRepository.save(foundUser);
		}
	}

	public List<Utilisateur> search(String username) {
		return (List<Utilisateur>) utilisateurRepository.findByUsername(username);
	}

	public String inscription(Utilisateur utilisateur) throws MessagingException, IOException {
		String encodedPassword = this.passwordEncoder.encode(utilisateur.getPassword());
		utilisateur.setPassword(encodedPassword);
		utilitaireService.validationChaine(utilisateur.getFirstName());
		valEmail(utilisateur.getUsername());
		valNumber(utilisateur.getUsername());
		utilisateurRepository.save(utilisateur);
		verificationService.createCode(utilisateur);
		Mail mail = new Mail();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", utilisateur.getFirstName());
		model.put("location", "United States");
		model.put("sign", "Java Developer");
		mail.setMailTo(utilisateur.getUsername());
		mail.setSubject("mail de confirmation");
		mail.setMailFrom("noreply@athena.com");
		mail.setProps(model);
		verificationService.sendEmail(mail);
		return "success";
	}

	public void connexion(Connexion connexion) {
	}
}
