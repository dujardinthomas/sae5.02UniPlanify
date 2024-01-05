package fr.sae502.uniplanify.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import fr.sae502.uniplanify.models.UserAccount;
import fr.sae502.uniplanify.models.repository.UserAccountRepository;

@Controller
public class MyControlleur {

    @Autowired
    private UserAccountRepository userAccountRepository;

    private UserAccount userAccount;

    @GetMapping(value = "/my")
    public String my(Principal principal) {
        String destination = "";

        try {
            userAccount = userAccountRepository.findByEmail(principal.getName());

            if (userAccount.isPro()) {
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
