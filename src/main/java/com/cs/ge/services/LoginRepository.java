package com.cs.ge.services;

import com.cs.ge.entites.Connexion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginRepository extends MongoRepository<Connexion, String> {
}
