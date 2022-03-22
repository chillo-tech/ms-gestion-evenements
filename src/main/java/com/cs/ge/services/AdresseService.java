package com.cs.ge.services;


import com.cs.ge.entites.Adresse;
import com.cs.ge.entites.Adresse;
import com.cs.ge.repositories.AdresseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdresseService {

	private final AdresseRepository adresseRepository;

	public AdresseService(AdresseRepository adresseRepository) {
		this.adresseRepository = adresseRepository;
	}

	public void creation(Adresse adresse) {
		String ville = adresse.getVille();
		ville = ville.toUpperCase();
		adresse.setVille(ville);

		// Enregistrer dans la BDD
		adresseRepository.save(adresse);
	}


	public List<Adresse> search() {
		return adresseRepository.findAll();
	}


	public void deleteAddress(String id) {
		adresseRepository.deleteById(id);
	}

	public void updateAddress(String id, Adresse adresse) {
		Optional<Adresse> current = this.adresseRepository.findById(id);
		if (current.isPresent()) {
			Adresse foundAdd = current.get();
			foundAdd.setId(id);
			foundAdd.setQuartier(adresse.getQuartier());
			foundAdd.setVille(adresse.getVille());
			adresseRepository.save(foundAdd);// LA METHODE SAVE VIENT DU FAIT QUE DANS UTILISATEURREPOSITORY J'AI HERITE
			// DE MONGOREPOSITORY QUI APPELLE CRUBREPOSITORY QUI METS AMA DIPOSITION SAVE

		}
	}


	List<Adresse> search(String quartier) {
		return adresseRepository.findByQuartier();
	}
}

