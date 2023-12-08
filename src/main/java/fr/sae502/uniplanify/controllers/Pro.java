package fr.sae502.uniplanify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.SessionBean;

@Controller
@RequestMapping(value = "/pro")
public class Pro {

    @Autowired
    private SessionBean sessionBean;

    @RequestMapping(value = "")
    public ModelAndView espacePro() {
        ModelAndView mav = new ModelAndView("pro");
        mav.addObject("user", sessionBean.getUtilisateur());
        return mav;
    }
}
