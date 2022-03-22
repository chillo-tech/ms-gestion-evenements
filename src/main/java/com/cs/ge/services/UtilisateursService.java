package com.cs.ge.services;

import com.cs.ge.entites.Utilisateur;
import com.cs.ge.repositories.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateursService {

	private final UtilisateurRepository utilisateurRepository;

	public UtilisateursService(UtilisateurRepository utilisateurRepository) {
		this.utilisateurRepository = utilisateurRepository;
	}


	public void add(Utilisateur utilisateur) { // en entrée je dois avoir quelque chose sous la forme d'un Utilisateur de type utilisateur
		String lastName = utilisateur.getLastName();
		lastName = lastName.toUpperCase();
		utilisateur.setLastName(lastName);

		// Enregistrer dans la BDD
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
			foundUser.setEmail(utilisateur.getEmail());
			utilisateurRepository.save(foundUser);

		}
	}


	List<Utilisateur> search(String email) {
		return utilisateurRepository.findByEmail();
	}
}
