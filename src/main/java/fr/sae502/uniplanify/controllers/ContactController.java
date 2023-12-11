package fr.sae502.uniplanify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.models.Contraintes;
import fr.sae502.uniplanify.repository.ContraintesRepository;

@Controller
public class ContactController {

    @Autowired
    private ContraintesRepository contraintesRepository;

    @RequestMapping("/contact")
    public ModelAndView contact() {
        ModelAndView mav = new ModelAndView("contact");
        mav.addObject("contraintes", ((Contraintes) contraintesRepository.findAll().iterator().next()));
        return mav;
    }
    
}
