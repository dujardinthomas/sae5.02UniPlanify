package fr.sae502.uniplanify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.sae502.uniplanify.models.Contraintes;
import fr.sae502.uniplanify.repository.ContraintesRepository;

@Controller
public class ContactController {

    @Autowired
    private ContraintesRepository contraintesRepository;

    @RequestMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contraintes", ((Contraintes) contraintesRepository.findAll().iterator().next()));
        return "contact";
    }
    
}
