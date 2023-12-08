package fr.sae502.uniplanify.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.sae502.uniplanify.SessionBean;
import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.UtilisateurRepository;

@Controller
public class Login {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private SessionBean sessionBean;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String email, @RequestParam String password, @RequestParam String origine) {
        Utilisateur user = utilisateurRepository.findByEmailAndPassword(email, password);
        if(user != null){
            sessionBean.setUtilisateur(user);
            sessionBean.setOrigine(origine);
            System.out.println("login verifié, on redirige vers " + origine);
            return "redirect:" + origine;  
        }else{
          return "redirect:/login?msg=inconnu";
        }
        
    }
}
