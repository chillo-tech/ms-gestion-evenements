package com.cs.ge.services;

import com.cs.ge.entites.Evenement;
import com.cs.ge.entites.Invite;
import com.cs.ge.entites.Mail;
import com.cs.ge.enums.EventStatus;
import com.cs.ge.exception.ApplicationException;
import com.cs.ge.repositories.EvenementsRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

@Service
public class EvenementsService {
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode.png";
    private final EvenementsRepository evenementsRepository;
    private final JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    public EvenementsService(final EvenementsRepository evenementsRepository, final JavaMailSender javaMailSender) {
        this.evenementsRepository = evenementsRepository;
        this.javaMailSender = javaMailSender;
    }

    private static EventStatus eventStatus(final Date dateDebut, final Date dateFin) {
        final Date date = new Date();
        EventStatus status = EventStatus.DISABLED;

        if (dateDebut.before(date)) {
            throw new ApplicationException("La date de votre évènement est invalide");
        }

        if (dateDebut.equals(date)) {
            status = EventStatus.ACTIVE;
        }

        if (dateFin.after(date)) {
            status = EventStatus.INCOMMING;
        }

        return status;
    }

    public static void generateQRCodeImage(final String text, final int width, final int height, final String filePath)
            throws WriterException, IOException {
        final QRCodeWriter qrCodeWriter = new QRCodeWriter();
        final BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        final Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    public static byte[] getQRCodeImage(final String text, final int width, final int height) throws WriterException, IOException {
        final QRCodeWriter qrCodeWriter = new QRCodeWriter();
        final BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        final ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        final byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }


    public List<Evenement> search() {
        return this.evenementsRepository.findAll();
    }

    public void add(final Evenement evenement) {

        if (evenement.getNom() == null || evenement.getNom().trim().isEmpty()) {
            throw new ApplicationException("Champs obligatoire");
        }
        final EventStatus status = EvenementsService.eventStatus(evenement.getDateDebut(), evenement.getDateFin());
        evenement.setStatut(status);

        //Adresse adresse = adresseRepository.save(evenement.getAdresse());
        //evenement.	setAdresse(adresse);

        //Utilisateur utilisateur= utilisateurRepository.save(evenement.getUtilisateur());
        //evenement.setUtilisateur(utilisateur);
        this.evenementsRepository.save(evenement);
    }

    public void deleteEvenement(final String id) {
        this.evenementsRepository.deleteById(id);
    }

    public void updateEvenement(final String id, final Evenement evenement) {
        final Optional<Evenement> current = this.evenementsRepository.findById(id);
        if (current.isPresent()) {
            final Evenement foundEvents = current.get();
            foundEvents.setId(id);
            foundEvents.setNom(evenement.getNom());
            foundEvents.setStatut(evenement.getStatut());
            foundEvents.setAdresse(evenement.getAdresse());
            foundEvents.setDateDebut(evenement.getDateDebut());
            foundEvents.setHeureDebut(evenement.getHeureDebut());
            foundEvents.setHeureFin(evenement.getHeureFin());
            this.evenementsRepository.save(foundEvents);
        }
    }

    public Evenement read(final String id) {
        return this.evenementsRepository.findById(id).orElseThrow(
                () -> new ApplicationException("Aucune enttité ne correspond au critères fournis")
        );
    }

    public void sendEmail(final Mail mail) throws MessagingException, IOException {
        final MimeMessage message = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        final Context context = new Context();
        context.setVariables(mail.getProps());
        final String html = this.templateEngine.process("templateQRCode", context);
        helper.setTo(mail.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getMailFrom());
        this.javaMailSender.send(message);
    }

    public String addInvites(final String id, final Invite invite) throws IOException, WriterException, MessagingException {
        final Evenement evenement = this.read(id);
        List<Invite> invites = evenement.getInvite();
        if (invites == null) {
            invites = new ArrayList<>();
        }
        invites.add(invite);
        evenement.setInvite(invites);
        this.evenementsRepository.save(evenement);
        final String url = "/src/main/java/resources/templateQRCode";
        generateQRCodeImage(url, 250, 250, QR_CODE_IMAGE_PATH);
        final Mail mail = new Mail();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", invite.getPrenomI());
        model.put("location", "United States");
        model.put("sign", "Java Developer");
        mail.setMailTo(invite.getNomI());
        mail.setSubject("QRCode mail");
        mail.setMailFrom("noreply@athena.com");
        mail.setProps(model);
        this.sendEmail(mail);
        return "success";
    }

}

