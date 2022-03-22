package com.cs.ge.repositories;


import com.cs.ge.entites.Adresse;
import com.cs.ge.entites.Adresse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AdresseRepository extends MongoRepository<Adresse, String> {// Il VA STOCKER UN ELEMENT DE TYPE ADRESSE QUI A UNE CLE PRIMAIRE DE TYPE STRING

	@Query("Select Adresse from adresse where Quartier Like e% ")
	List<Adresse> findByQuartier();

}

