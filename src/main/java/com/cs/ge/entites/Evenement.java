package com.cs.ge.entites;

import com.cs.ge.enums.EventStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

@Document("EVENEMENTS")
@AllArgsConstructor
@Getter
@Setter
public class Evenement {
    @Id
    private String id;
    private String nom;
    private Date dateDebut;
    private Date dateFin;
    private String heureDebut;
    private String heureFin;
    private EventStatus statut;
    private List<Invite> invite;
    private Utilisateur utilisateur;    
    private Adresse adresse;



}
