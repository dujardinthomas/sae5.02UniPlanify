package fr.sae502.uniplanify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.UtilisateurRepository;

@Controller
@RequestMapping(value = "/pro")
public class Pro {

    UtilisateurRepository utilisateurRepository;

    @RequestMapping(value = "")
    public ModelAndView espacePro() {
        ModelAndView mav = new ModelAndView("pro");
        mav.addObject("user", "f");
        return mav;
    }
}
