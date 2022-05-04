package com.cs.ge.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("CONNEXION")
@AllArgsConstructor
@Getter
@Setter
public class Connexion {

    private String username;
    private String password;
}
