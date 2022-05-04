package com.cs.ge.configuration;

import com.cs.ge.entites.Utilisateur;
import com.cs.ge.enums.Role;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@ChangeUnit(order = "001", id = "createUser", author = "athena")
public class DatabaseChangeLog {
    private final MongoTemplate mongoTemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DatabaseChangeLog(final MongoTemplate mongoTemplate,
                             final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.mongoTemplate = mongoTemplate;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Execution
    public void createUser() {
        final Utilisateur achille = new Utilisateur();
        achille.setRole(Role.ADMIN);
        achille.setFirstName("achille");
        achille.setLastName("Mbougueng");
        achille.setUsername("achille.mbougueng@gmail.com");
        final String password = this.bCryptPasswordEncoder.encode("ChilloEvents");
        achille.setPassword(password);

        final Utilisateur athena = new Utilisateur();
        athena.setRole(Role.ADMIN);
        athena.setFirstName("athena");
        athena.setLastName("Kuitsouc");
        athena.setUsername("kuitsdan@gmail.com");
        final String password1 = this.bCryptPasswordEncoder.encode("AtheEvents");
        athena.setPassword(password1);
        this.mongoTemplate.save(athena);

    }

    @RollbackExecution
    public void rollback() {
    }

}
