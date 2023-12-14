package fr.sae502.uniplanify.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.UtilisateurRepository;

@Controller
public class Inscription {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/inscription")
    public String inscription() {
        return "inscription";
    }

    @PostMapping("/inscription")
    public String inscriptionPost(@RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String origine) {
        Utilisateur user = new Utilisateur(nom, prenom, email, password, false, "../img/profils/default.jpg");
        utilisateurRepository.save(user);
        System.out.println("Utilisateur " + user.getEmail() + "enregistré");
        return "redirect:/login?msg=UtilisateurEnregistré&origine=" + origine;
    }

}
