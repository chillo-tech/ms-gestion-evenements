package com.cs.ge.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QRCodeGeneratorService {

    public static String generateQrCodeBase64(final String qrCodeContentToGenerate, final int width, final int height) {
        try {
            final QRCodeWriter qrCodeWriter = new QRCodeWriter();
            final BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContentToGenerate, BarcodeFormat.QR_CODE, width, height);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
            final String qrCodeBase64 = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            return qrCodeBase64;
        } catch (final WriterException ex) {
        } catch (final IOException ex) {
        }
        return null;
    }

}
