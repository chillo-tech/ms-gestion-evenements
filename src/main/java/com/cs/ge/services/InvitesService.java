package com.cs.ge.services;

import com.cs.ge.entites.Invite;
import com.cs.ge.repositories.InvitesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvitesService {
	private final InvitesRepository invitesRepository;

	public InvitesService(InvitesRepository invitesRepository) {
		this.invitesRepository = invitesRepository;
	}

	public List<Invite> Search() {
		return invitesRepository.findAll();
	}

	public void add(Invite invite) {
		invitesRepository.save(invite);
	}

	public void deleteInvite(String id) {
		invitesRepository.deleteById(id);
	}

	public void UpdateInvite(String id, Invite invite) {
		Optional<Invite> current = this.invitesRepository.findById(id);
		if (current.isPresent()) {
			Invite foundInvite = current.get();
			foundInvite.setId(id);
			foundInvite.setNomI(invite.getNomI());
			foundInvite.setPrenomI(invite.getPrenomI());
			foundInvite.setClasse(invite.getClasse());
			foundInvite.setPlace(invite.getPlace());
			foundInvite.setTypeBillet(invite.getTypeBillet());
			invitesRepository.save(foundInvite);
		}
	}

}
