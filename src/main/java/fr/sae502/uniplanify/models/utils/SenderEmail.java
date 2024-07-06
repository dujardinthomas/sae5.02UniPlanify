package fr.sae502.uniplanify.models.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
@Controller
@Component
@Service
public class SenderEmail {

    //TODO : VOIR POURQUOI CA RECUPERE PAS LES VALEURS DANS LE APPLICATION.PROPERTIES !!!!
    @Value("${mail.setfrom}")
    private String setFrom = "uniplanify@gmail.com";

    @Autowired
    @Value("${url.server}")
    public String urlServer = "";

    public boolean sendEmail(JavaMailSender mailSender, String to, String subject, String text) {
        text = text.replace("{site}", urlServer);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            System.out.println("setfrom : ");
            System.out.println(setFrom);
            helper.setFrom(setFrom);
            // connexion dans application.properties
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            System.out.println("on va envoyer le mail à " + to + " : avec comme contenu:\n" + text + "...");
            mailSender.send(message);
            System.out.println("Mail envoyé !");
            return true;
        } catch (Exception e) {
            System.out.println("Erreur lors de l'envoie du mail : " + e.getMessage());
            return false;
        }
    }

    //retourne le template de mail
    public String readEmailTemplate(Resource emailTemplate) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(emailTemplate.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Unable to read email template", e);
        }
    }
}
