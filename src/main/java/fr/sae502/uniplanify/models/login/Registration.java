package fr.sae502.uniplanify.models.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.sae502.uniplanify.models.UserAccount;
import fr.sae502.uniplanify.models.repository.UserAccountRepository;

@Controller
public class Registration {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @GetMapping("/inscription")
    public String registration() {
        return "inscription";
    }

    @PostMapping("/inscription")
    public String registrationPost(@RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false, defaultValue = "login") String origine) {

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
        System.out.println("on redirige sur "+origine);
        return "redirect:/"+origine;
    }

}
