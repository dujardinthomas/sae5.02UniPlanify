package fr.sae502.uniplanify.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.UtilisateurRepository;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@Component
public class Login{

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("message", "Veuillez vous connecter");
        mav.addObject("origine", request.getAttribute("origine"));
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String login, @RequestParam String pwd, @RequestParam String origine) {
        ModelAndView mav = new ModelAndView();
        Utilisateur user = utilisateurRepository.findByEmailAndPassword(login, pwd);

        if (user != null && user.getPassword().equals(pwd)) { // c'est un compte existant
            mav.addObject("message", "Bienvenue " + login);
            mav.addObject("userDTO", user);
            mav.setViewName("redirect:/"); // Redirection vers "/dashboard"
            System.out.println("user : " + user);

        } else {
            // non authentifié, on repart au login
            mav.addObject("message", "impossible de se connecter à votre compte avec le login : " + login);
            mav.setViewName("login");
        }
        return mav;
    }
}
