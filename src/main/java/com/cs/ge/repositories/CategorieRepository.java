package com.cs.ge.repositories;

import com.cs.ge.entites.Categorie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategorieRepository extends MongoRepository<Categorie, String> {

}
