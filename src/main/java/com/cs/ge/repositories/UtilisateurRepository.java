package com.cs.ge.repositories;
import com.cs.ge.entites.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {
	Utilisateur findByUsername(String username);
}


