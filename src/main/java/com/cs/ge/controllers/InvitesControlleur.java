package com.cs.ge.controllers;

import com.cs.ge.entites.Invite;
import com.cs.ge.services.InvitesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "invite", produces = "Application/json")
public class InvitesControlleur {

	private final InvitesService invitesService;

	public InvitesControlleur(InvitesService invitesService) {
		this.invitesService = invitesService;

	}

	@GetMapping
	public List<Invite> Search() {
		return invitesService.Search();
	}

	@PostMapping
	public void add(@RequestBody Invite invite) {
		invitesService.add(invite);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable String id) {
		invitesService.deleteInvite(id);
	}

	@PutMapping(value = "/{id}")
	public void Update(@PathVariable String id, @RequestBody Invite invite) {
		invitesService.UpdateInvite(id, invite);
	}
}


