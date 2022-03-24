package com.cs.ge.entites;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document ("ADRESSES")
public class Adresse {
    @Id
    private String id;
    private String ville;
    private String quartier;

    public Adresse() {
    }

    public Adresse(String id, String ville, String quartier) {
        this.id = id;
        this.ville = ville;
        this.quartier = quartier;
    }

    public String getId() {
        return id;
    }

    public String getVille() {
        return ville;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }
}

