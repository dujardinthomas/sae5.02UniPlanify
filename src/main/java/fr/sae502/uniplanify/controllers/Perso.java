package fr.sae502.uniplanify.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
