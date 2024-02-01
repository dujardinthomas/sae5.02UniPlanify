package fr.sae502.uniplanify.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.sae502.uniplanify.models.UserAccount;
import fr.sae502.uniplanify.models.repository.UserAccountRepository;
import fr.sae502.uniplanify.models.utils.SenderEmail;

@Controller
public class RegistrationController {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private JavaMailSender sender;

    @GetMapping("/inscription")
    public String registration() {
        return "inscription";
    }

    @PostMapping("/inscription")
    public String registrationPost(@RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false, defaultValue = "login") String origine) throws UnsupportedEncodingException {

        if(nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("champs vides");
            return "redirect:/inscription?msg=" + URLEncoder.encode("champs vides !", StandardCharsets.UTF_8.toString());
        }

        if(userAccountRepository.findByEmail(email) != null)  {
            System.out.println("email déjà utilisé");
            return "redirect:/inscription?msg=" + URLEncoder.encode("email déja utilisé !", StandardCharsets.UTF_8.toString());
        }

        String authority = "ROLE_USER";
        if(!userAccountRepository.findById(1).isPresent()) {
            System.out.println("pas d'utilisateur enregistré, on l'enregistre en pro");
            authority = "ROLE_ADMIN";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        UserAccount user = new UserAccount(nom, prenom, email, hashedPassword, "../img/profils/default.jpg", authority, true);
        userAccountRepository.save(user);
        System.out.println("Utilisateur " + user + " : enregistré");

        SenderEmail senderEmail = new SenderEmail();
        senderEmail.sendEmail(sender, user.getEmail(), "Bienvenue sur Uniplanify !", 
            "Bonjour "+prenom+",\n\nBienvenue sur Uniplanify !\n\nVous pouvez dès à présent vous connecter à votre espace personnel et prendre rdv chez nous !\n\nCordialement,\n\nL'équipe Uniplanify");


        System.out.println("on redirige sur "+origine);
        return "redirect:/"+origine;
    }

}
