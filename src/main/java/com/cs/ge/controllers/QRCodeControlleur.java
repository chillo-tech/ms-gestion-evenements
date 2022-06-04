package com.cs.ge.controllers;

import com.cs.ge.services.EvenementsService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QRCodeControlleur {
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode.png";
    private final EvenementsService evenementsService;

    public QRCodeControlleur(final EvenementsService evenementsService) {
        this.evenementsService = evenementsService;
    }
}
