package fr.sae502.uniplanify.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Deconnexion {

    @Autowired
    private SessionBean sessionBean;

    @GetMapping("/deconnexion")
    public String deconnexion() {
        sessionBean.setUtilisateur(null);
        return "redirect:/";
    }
}
