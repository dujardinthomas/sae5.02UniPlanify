package fr.sae502.uniplanify.controllers;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.UtilisateurRepository;

@Component
@Controller
public class Inscription {

    private UtilisateurRepository utilisateurRepository;

    public Inscription(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }
    
    @GetMapping("/inscription")
    public ModelAndView inscription() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("inscription");
        return modelAndView;
    }

    @PostMapping("/inscription")
    public void inscriptionPost(@RequestParam("nom") String nom,
                                @RequestParam("prenom") String prenom,
                                @RequestParam("email") String email, 
                                @RequestParam("password") String password) {
        utilisateurRepository.save(new Utilisateur(nom, prenom, email, password, false));

    }
}
