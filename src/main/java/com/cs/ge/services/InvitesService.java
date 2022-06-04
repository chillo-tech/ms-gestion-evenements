package com.cs.ge.services;

import com.cs.ge.entites.Invite;
import com.cs.ge.exception.ApplicationException;
import com.cs.ge.repositories.InvitesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvitesService {
    private final InvitesRepository invitesRepository;

    public InvitesService(final InvitesRepository invitesRepository) {
        this.invitesRepository = invitesRepository;
    }

    public List<Invite> Search() {
        return this.invitesRepository.findAll();
    }

    public void add(final Invite invite) {
        this.validationUsername(invite.getUsername());
		this.invitesRepository.save(invite);
    }

    public void deleteInvite(final String id) {
		this.invitesRepository.deleteById(id);
    }

    public void UpdateInvite(final String id, final Invite invite) {
        final Optional<Invite> current = this.invitesRepository.findById(id);
        if (current.isPresent()) {
            final Invite foundInvite = current.get();
            foundInvite.setId(id);
            foundInvite.setNomI(invite.getNomI());
            foundInvite.setPrenomI(invite.getPrenomI());
            foundInvite.setClasse(invite.getClasse());
            foundInvite.setPlace(invite.getPlace());
            foundInvite.setTypeBillet(invite.getTypeBillet());
            foundInvite.setUsername(invite.getUsername());
			this.invitesRepository.save(foundInvite);
        }
    }

    public void validationUsername(final String username) {
        final Optional<Invite> exist = Optional.ofNullable(this.invitesRepository.findByUsername(username));
        if (exist.isPresent()) {
            throw new ApplicationException("Username existe déjà");
        }
    }

}
