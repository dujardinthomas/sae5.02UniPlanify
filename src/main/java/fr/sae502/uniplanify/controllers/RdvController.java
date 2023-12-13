package fr.sae502.uniplanify.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/rdv")
public class RdvController {
    @GetMapping("/confirmSuppressionRDV")
    public String modifyRDV() {
        return "confirmSuppressionRDV";
    }

    @PostMapping("/confirmSuppressionRDV")
    public ModelAndView confirmSuppressionRDV() {
        ModelAndView mav = new ModelAndView("confirmSuppressionRDV");
        return mav;
    }
}
