package com.cs.ge.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Document("VERIFICATION")
@AllArgsConstructor
@Getter
@Setter
public class Verification {
    @Id
    private String id;
    private String code;
    private String username;
    private LocalDateTime dateCreation;
    private LocalDateTime dateExpiration;
    private Utilisateur utilisateur;

    public Verification() {
    }
    public Verification (String code, String username,LocalDateTime dateCreation,LocalDateTime dateExpiration, Utilisateur utilisateur){
        this.code=code;
        this.username=username;
        this.dateCreation=dateCreation;
        this.dateExpiration=dateExpiration;
        this.utilisateur=utilisateur;
    }
}
