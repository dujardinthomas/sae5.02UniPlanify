package fr.sae502.uniplanify.models;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;

public class EnvoieUnMail {

    public boolean envoieMail(JavaMailSender sender, String to, String subject, String text) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("thomas.dujardin2.etu@univ-lille.fr");
            //connexion dans application.properties
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            System.out.println("on va envoyer le mail à " + to + " : avec comme contenu :\n" + text + "...");
            sender.send(message);
            System.out.println("Mail envoyé !");
            return true;
        } catch (Exception e) {
            System.out.println("Erreur lors de l'envoie du mail : " + e.getMessage());
            return false;
        }
    }
}
