package com.cs.ge.services;

import com.cs.ge.entites.Mail;
import com.cs.ge.entites.Utilisateur;
import com.cs.ge.entites.Verification;
import com.cs.ge.exception.ApplicationException;
import com.cs.ge.repositories.VerificationRepository;
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
import java.time.LocalDateTime;

@Service
public class VerificationService {
    private final VerificationRepository verificationRepository;
    private final JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    public VerificationService(final VerificationRepository verificationRepository, final JavaMailSender javaMailSender) {
        this.verificationRepository = verificationRepository;
        this.javaMailSender = javaMailSender;
    }

    public Verification getByCode(final String code) {
        return this.verificationRepository.findByCode(code).orElseThrow(
                () -> new ApplicationException("le code d'activation n'est pas disponible"));
    }

    public void createCode(final Utilisateur utilisateur) {
        final Verification verification = new Verification();
        verification.setUsername(utilisateur.getUsername());
        final String randomCode = RandomString.make(4);
        verification.setCode(randomCode);
        verification.setDateCreation(LocalDateTime.now());
        verification.setDateExpiration(LocalDateTime.now().plusMinutes(15));
        verification.setUtilisateur(utilisateur);
        this.verificationRepository.save(verification);
    }

    public void sendEmail(final Mail mail) throws MessagingException, IOException {
        final MimeMessage message = this.javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        final Context context = new Context();
        context.setVariables(mail.getProps());
        final String html = this.templateEngine.process("template", context);
        helper.setTo(mail.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getMailFrom());
        this.javaMailSender.send(message);
    }
}