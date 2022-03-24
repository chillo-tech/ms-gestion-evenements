package com.cs.ge.services;
import com.cs.ge.entites.Mail;
import com.cs.ge.exception.ApplicationException;
import com.cs.ge.repositories.VerificationRepository;
import com.cs.ge.entites.Verification;
import com.cs.ge.entites.Utilisateur;
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
    private VerificationRepository verificationRepository;
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    public VerificationService(VerificationRepository verificationRepository, JavaMailSender javaMailSender) {
        this.verificationRepository = verificationRepository;
        this.javaMailSender = javaMailSender;
    }

    public  Verification getByCode(String code) {
        return  this.verificationRepository.findByCode(code).orElseThrow(
               () -> new ApplicationException("le code d'activation n'est pas disponible"));
    }

    public void createCode (Utilisateur utilisateur) {
        Verification verification = new Verification();
        verification.setUsername(utilisateur.getUsername());
        String randomCode = RandomString.make(4);
        verification.setCode(randomCode);
        verification.setDateCreation(LocalDateTime.now());
        verification.setDateExpiration(LocalDateTime.now().plusMinutes(15));
        verification.setUtilisateur(utilisateur);
        verificationRepository.save(verification);
    }

    public void sendEmail(Mail mail) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(mail.getProps());
        String html = templateEngine.process("template", context);
        helper.setTo(mail.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getMailFrom());
        javaMailSender.send(message);
    }
}