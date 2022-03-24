package com.cs.ge.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document("MAIL")
@AllArgsConstructor
@Getter
@Setter
public class Mail {

    private String mailFrom;
    private String mailTo;
    private String subject;
    private Map<String, Object> props;

    public Mail() {

    }
}
