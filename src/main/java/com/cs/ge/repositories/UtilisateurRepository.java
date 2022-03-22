package com.cs.ge.repositories;

import com.cs.ge.entites.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {
	@Query("Select Utilisateur from Utilisateur where Email Like %yahoo% ")
	List<Utilisateur> findByEmail();

}
