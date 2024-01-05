package fr.sae502.uniplanify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.sae502.uniplanify.models.ConstraintPro;
import fr.sae502.uniplanify.models.repository.ConstraintProRepository;

@Controller
public class ContactController {

    @Autowired
    private ConstraintProRepository constraintProRepository;

    @RequestMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contrainte", ((ConstraintPro) constraintProRepository.findAll().iterator().next()));
        return "contact";
    }
    
}
