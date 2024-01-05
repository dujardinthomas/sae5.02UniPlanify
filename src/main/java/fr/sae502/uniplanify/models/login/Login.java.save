package fr.sae502.uniplanify.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Login {

    // @Autowired
    // private UtilisateurRepository utilisateurRepository;

    // @Autowired
    // private SessionBean sessionBean;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // @PostMapping("/login")
    // public String loginPost(@RequestParam String email, @RequestParam String password, @RequestParam String origine) {
    //     Utilisateur user = utilisateurRepository.findByEmailAndPassword(email, password);
    //     if(user != null){
    //         sessionBean.setUtilisateur(user);
    //         sessionBean.setOrigine(origine);
    //         System.out.println("login verifié, on redirige vers " + origine);
    //         return "redirect:" + origine;  
    //     }else{
    //       return "redirect:/login?msg=inconnu";
    //     }
        
    // }
}
