package com.cs.ge.repositories;

import com.cs.ge.entites.Invite;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvitesRepository extends MongoRepository<Invite, String> {
    Invite findByUsername(String username);
}

