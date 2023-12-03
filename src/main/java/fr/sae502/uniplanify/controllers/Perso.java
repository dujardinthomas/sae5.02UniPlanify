package fr.sae502.uniplanify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.models.Utilisateur;

@Controller
@RequestMapping(value = "/my")
public class Perso {

    private Utilisateur user;

    @RequestMapping(value = "/")
    public ModelAndView espacePro() {
        ModelAndView mav = new ModelAndView("perso");
        mav.addObject("user", user);
        return mav;
    }
}
