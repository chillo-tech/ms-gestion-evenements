package com.cs.ge.controllers;

import com.cs.ge.entites.Evenement;
import com.cs.ge.entites.Invite;
import com.cs.ge.services.EvenementsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
	@RequestMapping(path = "evenement", produces = "application/json")
public class EvenementsControlleur {

	private final EvenementsService evenementsService;

	public EvenementsControlleur(EvenementsService evenementsService) {
		this.evenementsService = evenementsService;

	}

	@GetMapping
	public List<Evenement> Search() {
		return evenementsService.search();
	}

	@PostMapping
	public void add(@RequestBody Evenement evenement) {evenementsService.add(evenement);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable String id) {
		evenementsService.deleteEvenement(id);
	}

	@PutMapping(value = "/{id}")
	public void update(@PathVariable String id, @RequestBody Evenement evenement) {
		evenementsService.updateEvenement(id, evenement);
	}

	@PostMapping(value = "{id}/invite")
	public void addInvites(@PathVariable String id, @RequestBody Invite invite){
		this.evenementsService.addInvites(id, invite);
	}
}
