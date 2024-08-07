package fr.sae502.uniplanify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.sae502.uniplanify.models.UserAccount;
import fr.sae502.uniplanify.models.repository.UserAccountRepository;
import fr.sae502.uniplanify.models.utils.SenderEmail;
import fr.sae502.uniplanify.models.utils.TokenGenerator;

@Controller
public class ResetPasswordController {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private JavaMailSender sender;

    private UserAccount userAccount;

    @Value("${url.server}")
    private String urlServer;

    @Value("classpath:static/email-template/passwordChanged.html")
    private Resource emailPasswordChanged;

    @Value("classpath:static/email-template/passwordReset.html")
    private Resource emailPasswordReset;

    @GetMapping("/reset-password-form")
    public String resetPassword() {
        return "reset-password-form";
    }

    @PostMapping("/reset-password-form")
    public String resetPasswordPost(Model model, @RequestParam("email") String email){
        try {
            userAccount = userAccountRepository.findByEmail(email);
            TokenGenerator tokenGenerator = new TokenGenerator();
            String token = tokenGenerator.generateToken(16);
            userAccount.setToken(token);
            userAccountRepository.save(userAccount);

            SenderEmail senderEmail = new SenderEmail();
            System.out.println("urlServer : ");
            System.out.println(senderEmail.urlServer);
            senderEmail.sendEmail(sender, userAccount.getEmail(), "Reinitialiser votre mot de passe Uniplanify !", 
                senderEmail.readEmailTemplate(emailPasswordReset)
                .replace("{prenom}", userAccount.getPrenom())
                .replace("{lien}", urlServer + "/reset-password?token=" + token)
            );

            model.addAttribute("msg", "Un email vous a été envoyé");
        } catch (Exception e) {
            System.out.println("Erreur lors de l'envoie du mail : " + e.getMessage());
            model.addAttribute("msg", "Email inconnu");
        }
        return "redirect:/login?msg=" + model.getAttribute("msg");
    }

    @GetMapping("/reset-password")
    public String resetPasswordToken(Model model, @RequestParam("token") String token) {
        model.addAttribute("token", token);
        return "reset-password";
    }


    @PostMapping("/reset-password")
    public String resetPasswordTokenPost(Model model, @RequestParam("token") String token, @RequestParam("password") String password) {
        try {
            userAccount = userAccountRepository.findByToken(token);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            userAccount.setPassword(hashedPassword);
            userAccount.setToken(null);
            userAccountRepository.save(userAccount);

            SenderEmail senderEmail = new SenderEmail();
            senderEmail.sendEmail(sender, userAccount.getEmail(), "Votre mot de passe a été modifié !",
                senderEmail.readEmailTemplate(emailPasswordChanged)
                    .replace("{prenom}", userAccount.getPrenom())
            );

            model.addAttribute("msg", "Mot de passe modifié");
        } catch (Exception e) {
            model.addAttribute("msg", "Erreur");
        }
        return "redirect:/login?msg=" + model.getAttribute("msg");
    }

    
}
