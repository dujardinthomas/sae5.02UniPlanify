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
@RequestMapping(value = "/pro")
public class Pro {

    @Autowired
    private SessionBean sessionBean;

    @Autowired 
    private RdvRepository rdvRepository;

    private Utilisateur user;

    @RequestMapping(value = {"", "/"})
    public ModelAndView espacePro() {
        user = sessionBean.getUtilisateur();
        ModelAndView mav = new ModelAndView("pro");
        mav.addObject("user", user);
        mav.addObject("rdvs", (List<Rdv>) rdvRepository.findAll());
        return mav;
    }
}
