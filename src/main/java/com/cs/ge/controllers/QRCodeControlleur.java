package com.cs.ge.controllers;

import com.cs.ge.services.EvenementsService;
import com.google.zxing.WriterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class QRCodeControlleur {
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode.png";
    private final EvenementsService evenementsService;

    public QRCodeControlleur(final EvenementsService evenementsService) {
        this.evenementsService = evenementsService;
    }

    @GetMapping(value = "/genrateAndDownloadQRCode/{codeText}/{width}/{height}")
    public void download(@PathVariable("codeText") final String codeText, @PathVariable("width") final Integer width, @PathVariable("height") final Integer height) throws IOException, WriterException {
        this.evenementsService.generateQRCodeImage(codeText, width, height, QR_CODE_IMAGE_PATH);
    }

    @GetMapping(value = "/genrateQRCode/{codeText}/{width}/{height}")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable("codeText") final String codeText, @PathVariable("width") final Integer width, @PathVariable("height") final Integer height)
            throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(this.evenementsService.getQRCodeImage(codeText, width, height));
    }
}
