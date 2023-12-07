package fr.sae502.uniplanify.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.classic.pattern.Util;
import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.UtilisateurRepository;

@Controller
@Component
public class Inscription {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/inscription")
    public ModelAndView inscriptionForm() {
        ModelAndView mav = new ModelAndView("inscription");
        mav.addObject("message", "Veuillez créer votre compte");
        return mav;
    }

    @PostMapping("/inscription")
    public ModelAndView inscriptionTraitement(
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("mail") String mail,
            @RequestParam("password") String password) {

        Utilisateur user = new Utilisateur(nom, prenom, mail, password, false);
        utilisateurRepository.save(user);
        ModelAndView mav = new ModelAndView();

        mav.addObject("message", "Votre compte à été correctement crée avec le login : " + mail);
        mav.addObject("userDTO", user);
        mav.setViewName("redirect:/login");
        System.out.println("user : " + user);

        return mav;
    }

}
