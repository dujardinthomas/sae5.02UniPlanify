package fr.sae502.uniplanify.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fr.sae502.uniplanify.models.Jour;
import fr.sae502.uniplanify.repository.ContraintesRepository;
import fr.sae502.uniplanify.repository.IndisponibiliteRepository;
import fr.sae502.uniplanify.repository.JourneeTypeProRepository;
import fr.sae502.uniplanify.repository.RdvRepository;

@Component
@Controller
public class JourController {

    @Autowired
    private JourneeTypeProRepository journeeTypeProRepository;
    @Autowired
    private ContraintesRepository constraintRepository;
    @Autowired
    private IndisponibiliteRepository indisponibiliteRepository;
    @Autowired
    private RdvRepository rdvRepository;

    @RequestMapping(value = "/jour")
    public String jour(@RequestParam(defaultValue = "0") int year,
            @RequestParam(defaultValue = "0") int month,
            @RequestParam(defaultValue = "0") int day, Model model) {

        LocalDate selectedDate = LocalDate.now();
        if (month == 0 || day == 0 || year == 0) {
            year = selectedDate.getYear();
            month = selectedDate.getMonthValue();
            day = selectedDate.getDayOfMonth();
        } else {
            selectedDate = LocalDate.of(year, month, day);
        }

        Jour jour = new Jour(selectedDate, constraintRepository, journeeTypeProRepository,
                indisponibiliteRepository, rdvRepository);        
        model.addAttribute("jour", jour);

        return "jour";
    }

}
