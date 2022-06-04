package com.cs.ge.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@Document("INVITES")
public class Invite {
    @Id
    private String id;
    private String nomI;
    private String prenomI;
    private String classe;
    private String place;
    private String typeBillet;
    private String username;

    public Invite() {
    }

}