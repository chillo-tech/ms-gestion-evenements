package com.cs.ge.configuration;

import com.cs.ge.entites.Categorie;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(order = "002", id = "createCat", author = "athena", runAlways = true)
public class DatabaseChangeLogC {

    private final MongoTemplate mongoTemplate;

    public DatabaseChangeLogC(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void createCat() {
        final Categorie categorie = new Categorie();
        categorie.setLibelle("Mariage");

        final Categorie categorie1 = new Categorie();
        categorie1.setLibelle("Anniversaire");

        final Categorie categorie2 = new Categorie();
        categorie2.setLibelle("Salon");


        this.mongoTemplate.save(categorie);

    }

    @RollbackExecution
    public void rollback() {
    }
}
