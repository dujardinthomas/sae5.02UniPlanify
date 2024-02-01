package fr.sae502.uniplanify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String connection(Model model, @RequestParam(required = false) String msg) {
        System.out.println(msg);
        model.addAttribute("msg", msg);
        return "login";
    }
    
}
