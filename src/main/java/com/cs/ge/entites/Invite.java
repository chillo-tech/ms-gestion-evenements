package com.cs.ge.entites;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("INVITES")
public class Invite {
    @Id
    private String id;
    private String nomI;
    private String prenomI;
    private String classe;
    private String place;
    private String typeBillet;

    public Invite() {
    }

    public Invite(String id, String nomI, String prenomI, String classe, String place, String typeBillet) {
        this.id = id;
        this.nomI = nomI;
        this.prenomI = prenomI;
        this.classe = classe;
        this.place = place;
        this.typeBillet = typeBillet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomI() {
        return nomI;
    }

    public void setNomI(String nomI) {
        this.nomI = nomI;
    }

    public String getPrenomI() {
        return prenomI;
    }

    public void setPrenomI(String prenomI) {
        this.prenomI = prenomI;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTypeBillet() {
        return typeBillet;
    }

    public void setTypeBillet(String typeBillet) {
        this.typeBillet = typeBillet;
    }
}
