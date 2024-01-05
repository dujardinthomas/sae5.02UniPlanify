package fr.sae502.uniplanify.controllers;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.sae502.uniplanify.models.repository.ConstraintProRepository;
import fr.sae502.uniplanify.models.repository.UnavailabilityRepository;
import fr.sae502.uniplanify.view.Monthly;
import fr.sae502.uniplanify.models.repository.TypicalDayProRepository;
import fr.sae502.uniplanify.models.repository.RdvRepository;

@Component
@Controller
public class MonthlyController {

    @Autowired
    private TypicalDayProRepository journeeTypeProRepository;
    @Autowired
    private UnavailabilityRepository indisponibiliteRepository;
    @Autowired
    private RdvRepository rdvRepository;
    @Autowired
    private ConstraintProRepository constraintProRepository;

    @RequestMapping(value = "/")
    @Transactional
    public String calendrier(@RequestParam(defaultValue = "0") int year, 
                                    @RequestParam(defaultValue = "0") int month, Model model) {

        LocalDate currentDate = LocalDate.now();
        if (month == 0) {
            year = currentDate.getYear();
            month = currentDate.getMonthValue();
        }
        
        Monthly monthly = new Monthly(year, month, constraintProRepository, journeeTypeProRepository, indisponibiliteRepository, rdvRepository);

        // Gestion des mois précédents et suivants
        int previousMonth = month == 1 ? 12 : month - 1;
        int previousYear = month == 1 ? year - 1 : year;
        
        int nextMonth = month == 12 ? 1 : month + 1;
        int nextYear = month == 12 ? year + 1 : year;

        model.addAttribute("previousYear", previousYear);
        model.addAttribute("previousMonth", previousMonth);
        model.addAttribute("nextYear", nextYear);
        model.addAttribute("nextMonth", nextMonth);
        model.addAttribute("calendrier", monthly);

        return "rdv/mensuel";
    }

    
}
