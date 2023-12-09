package fr.sae502.uniplanify.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.SessionBean;
import fr.sae502.uniplanify.models.CleCompositeRDV;
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
        
        // Obtenir les rendez-vous uniquement pour les utilisateurs concernés
        // CleCompositeRDV cle = new CleCompositeRDV();
        // cle.setHeure(LocalTime.of(9, 0, 0));
        // cle.setJour(LocalDate.of(2023, 12, 12));
        // Rdv rdv = rdvRepository.findById(cle).get();
        // List<Rdv> rdvs = new ArrayList<>();
        // rdvs.add(rdv);
        List<Rdv> rdvs = rdvRepository.findRdvsByClientId(user.getId());
        mav.addObject("rdvs", rdvs);
        
        return mav;
    }

}
