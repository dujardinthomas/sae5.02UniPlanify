package fr.sae502.uniplanify.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.login.SessionBean;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.RdvRepository;
import fr.sae502.uniplanify.repository.UtilisateurRepository;

@Controller
@RequestMapping(value = "/perso")
public class PersoController {

    @Autowired
    private SessionBean sessionBean;

    @Autowired 
    private RdvRepository rdvRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private Utilisateur user;

    @RequestMapping(value = {"", "/"})
    public ModelAndView espacePerso() {
        user = sessionBean.getUtilisateur();
        if(user.isPro()){
            return new ModelAndView("redirect:/pro");
        }
        ModelAndView mav = new ModelAndView("perso");
        mav.addObject("user", user);
        List<Rdv> rdvs = rdvRepository.findRdvsByClientId(user.getId());
        mav.addObject("rdvs", rdvs);
        
        return mav;
    }

    @GetMapping(value = "/profil")
    public ModelAndView getProfil(){
        ModelAndView mav = new ModelAndView("profil");
        mav.addObject("user", user);
        mav.addObject("origine", "redirect:/perso");
        return mav;
    }

    @PostMapping(value = "/profil")
    public ModelAndView majProfil(
        @RequestParam(value="nom", defaultValue = "") String nom,
        @RequestParam(value="prenom", defaultValue = "") String prenom,
        @RequestParam(value="email", defaultValue = "") String email,
        @RequestParam(value="password", defaultValue = "") String password,
        @RequestParam(value="origine", defaultValue = "/") String origine){
        
        ModelAndView mav = new ModelAndView(origine);
        user = sessionBean.getUtilisateur();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setPassword(password);
        sessionBean.setUtilisateur(user);
        System.out.println("dans la base : ? " + utilisateurRepository.save(user));
        return mav;
    }

}
