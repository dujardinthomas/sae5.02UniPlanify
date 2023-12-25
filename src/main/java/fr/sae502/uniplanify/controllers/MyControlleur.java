package fr.sae502.uniplanify.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.UtilisateurRepository;

@Controller
public class MyControlleur {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private Utilisateur user;

    @GetMapping(value = "/my")
    public String my(Principal principal) {
        String destination = "";

        try {
            user = utilisateurRepository.findByEmail(principal.getName());

            if (user.isPro()) {
                destination = "redirect:/pro";
            } else {
                destination = "redirect:/perso";
            }
        } catch (Exception e) {
            destination = "redirect:/login";
        }

        return destination;
    }
}
