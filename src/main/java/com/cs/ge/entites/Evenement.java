package com.cs.ge.entites;

import com.cs.ge.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("EVENEMENTS")
@AllArgsConstructor
@NoArgsConstructor
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
    private Categorie categorie;

}
