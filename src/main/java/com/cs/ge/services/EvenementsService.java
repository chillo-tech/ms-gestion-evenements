package com.cs.ge.services;

import com.cs.ge.entites.Evenement;
import com.cs.ge.entites.Invite;
import com.cs.ge.entites.Mail;
import com.cs.ge.enums.EventStatus;
import com.cs.ge.exception.ApplicationException;
import com.cs.ge.repositories.EvenementsRepository;
import com.cs.ge.utilitaire.UtilitaireService;
import com.google.zxing.WriterException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class EvenementsService {
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode.png";
    private final EvenementsRepository evenementsRepository;
    private final JavaMailSender javaMailSender;
    private final QRCodeGeneratorService qrCodeGeneratorService;
    @Autowired
    private SpringTemplateEngine templateEngine;

    public EvenementsService(final EvenementsRepository evenementsRepository, final JavaMailSender javaMailSender, final QRCodeGeneratorService qrCodeGeneratorService) {
        this.evenementsRepository = evenementsRepository;
        this.javaMailSender = javaMailSender;
        this.qrCodeGeneratorService = qrCodeGeneratorService;
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
        UtilitaireService.valEmail(invite.getUsername());
        UtilitaireService.valNumber(invite.getUsername());
        List<Invite> invites = evenement.getInvite();
        if (invites == null) {
            invites = new ArrayList<>();
        }
        invites.add(invite);
        evenement.setInvite(invites);
        this.evenementsRepository.save(evenement);
        final String randomCode = RandomString.make(4);
        invite.setId(UUID.randomUUID().toString());
        final String text = String.format("{'user':'%s','event':'%s'}", invite.getId(), evenement.getId());
        final String image = QRCodeGeneratorService.generateQrCodeBase64(text, 200, 200);
        final Mail mail = new Mail();
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", invite.getPrenomI());
        model.put("image", image);
        model.put("sign", "Java Developer");
        mail.setMailTo(invite.getUsername());
        mail.setSubject("QRCode mail");
        mail.setMailFrom("noreply@athena.com");
        mail.setProps(model);
        this.sendEmail(mail);
        return "success";
    }
}

