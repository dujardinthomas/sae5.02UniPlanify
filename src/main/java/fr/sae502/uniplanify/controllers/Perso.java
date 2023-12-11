package fr.sae502.uniplanify.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.SessionBean;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.RdvRepository;

@Controller
@RequestMapping(value = "/perso")
public class Perso {

    @Autowired
    private SessionBean sessionBean;

    @Autowired 
    private RdvRepository rdvRepository;

    private Utilisateur user;

    @RequestMapping(value = {"", "/"})
    public ModelAndView espacePerso() {
        user = sessionBean.getUtilisateur();
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
        
        System.out.println("nom : " + nom);
        System.out.println("prenom : " + prenom);
        System.out.println("email : " + email);
        System.out.println("password : " + password);
        System.out.println("origine : " + origine);
        
        ModelAndView mav = new ModelAndView(origine);
        
        return mav;
    }

}
