package com.cs.ge.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("CATEGORIE")
public class Categorie {
    private String id;
    private String libelle;
    private String description;
    private String image;
}
