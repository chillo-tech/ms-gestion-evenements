package com.cs.ge.repositories;

import com.cs.ge.entites.Evenement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EvenementsRepository extends MongoRepository<Evenement, String> {

}
