package fr.sae502.uniplanify.models;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.internet.MimeMessage;

public class EnvoieUnMail {

    public boolean envoieMail(JavaMailSender mailSender, String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("uniplanify@gmail.com");
            // connexion dans application.properties
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            System.out.println("on va envoyer le mail à " + to + " : avec comme contenu:\n" + text + "...");
            mailSender.send(message);
            System.out.println("Mail envoyé !");
            return true;
        } catch (Exception e) {
            System.out.println("Erreur lors de l'envoie du mail : " + e.getMessage());
            return false;
        }
    }
}
