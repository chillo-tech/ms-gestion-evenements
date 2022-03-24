package com.cs.ge.repositories;
import com.cs.ge.entites.Verification;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface VerificationRepository extends MongoRepository<Verification, String> {
      Optional<Verification> findByCode(String code);
}


