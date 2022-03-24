package com.cs.ge.services;

import com.cs.ge.entites.Adresse;
import com.cs.ge.entites.Evenement;
import com.cs.ge.entites.Invite;
import com.cs.ge.entites.Utilisateur;
import com.cs.ge.enums.EventStatus;
import com.cs.ge.repositories.AdresseRepository;
import com.cs.ge.repositories.EvenementsRepository;
import com.cs.ge.repositories.UtilisateurRepository;
import com.cs.ge.exception.ApplicationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EvenementsService {
	private final EvenementsRepository evenementsRepository;
	//private final AdresseRepository adresseRepository;
	//private final UtilisateurRepository utilisateurRepository;

	public EvenementsService(EvenementsRepository evenementsRepository, AdresseRepository adresseRepository,UtilisateurRepository utilisateurRepository) {
		this.evenementsRepository = evenementsRepository;
		//this.adresseRepository = adresseRepository;
		//this.utilisateurRepository = utilisateurRepository;
	}

	public List<Evenement> search() {
		return evenementsRepository.findAll();
	}

	public void add(Evenement evenement ) {

		if (evenement.getNom() == null || evenement.getNom().trim().isEmpty()){
			throw new ApplicationException("Champs obligatoire");
		}

		EventStatus status = eventStatus(evenement.getDateDebut(), evenement.getDateFin());
		evenement.setStatut(status);

		//Adresse adresse = adresseRepository.save(evenement.getAdresse());
	    //evenement.setAdresse(adresse);

		//Utilisateur utilisateur= utilisateurRepository.save(evenement.getUtilisateur());
		//evenement.setUtilisateur(utilisateur);

		evenementsRepository.save(evenement);

	}

	private EventStatus eventStatus(Date dateDebut,Date dateFin ) {
		Date date = new Date();
		EventStatus status = EventStatus.DISABLED;

		if (dateDebut.before(date)){
			throw new ApplicationException("La date de votre évènement est invalide");
		}

		if (dateDebut.equals(date)) {
			status = EventStatus.ACTIVE;
		}

		if (dateFin.after(date)){
			status = EventStatus.INCOMMING;
		}

		return status;
	}

	public void deleteEvenement(String id) {
		evenementsRepository.deleteById(id);
	}

	public void updateEvenement(String id, Evenement evenement) {
		Optional<Evenement> current = this.evenementsRepository.findById(id);
		if (current.isPresent()) {
			Evenement foundEvents = current.get();
			foundEvents.setId(id);
			foundEvents.setNom(evenement.getNom());
			foundEvents.setStatut(evenement.getStatut());
			foundEvents.setAdresse(evenement.getAdresse());
			foundEvents.setDateDebut(evenement.getDateDebut());
			foundEvents.setHeureDebut(evenement.getHeureDebut());
			foundEvents.setHeureFin(evenement.getHeureFin());
			evenementsRepository.save(foundEvents);
		}
	}
	public Evenement read(String id) {
		return this.evenementsRepository.findById(id).orElseThrow(
				() -> new ApplicationException("Aucune enttité ne correspond au critères fournis")
		);
	}
	public void addInvites(String id, Invite invite) {
		Evenement evenement = this.read(id);
		List<Invite> invites = evenement.getInvite();
		if(invites == null) {
			invites = new ArrayList<>();
		}
		invites.add(invite);
		evenement.setInvite(invites);
		this.evenementsRepository.save(evenement);
	}
}
