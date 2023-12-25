package fr.sae502.uniplanify.controllers;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.sae502.uniplanify.models.Semaine;
import fr.sae502.uniplanify.repository.ContraintesRepository;
import fr.sae502.uniplanify.repository.IndisponibiliteRepository;
import fr.sae502.uniplanify.repository.JourneeTypeProRepository;
import fr.sae502.uniplanify.repository.RdvRepository;

@Component
@Controller
public class SemaineController {

    @Autowired
    private JourneeTypeProRepository journeeTypeProRepository;
    @Autowired
    private IndisponibiliteRepository indisponibiliteRepository;
    @Autowired
    private RdvRepository rdvRepository;
    @Autowired
    private ContraintesRepository constraintRepository;

    @RequestMapping(value = "/week")
    @Transactional
    public String semaine(@RequestParam(defaultValue = "0") int dayDebut,
            @RequestParam(defaultValue = "0") int monthDebut,
            @RequestParam(defaultValue = "0") int yearDebut,
            @RequestParam(defaultValue = "0") int dayFin,
            @RequestParam(defaultValue = "0") int monthFin,
            @RequestParam(defaultValue = "0") int yearFin,
            Model model) {

        LocalDate currentDate = LocalDate.now();
        if (dayDebut == 0) {
            dayDebut = currentDate.getDayOfMonth();
            monthDebut = currentDate.getMonthValue();
            yearDebut = currentDate.getYear();

            dayFin = currentDate.plusDays(6).getDayOfMonth();
            monthFin = currentDate.plusDays(6).getMonthValue();
            yearFin = currentDate.plusDays(6).getYear();

        }

        LocalDate startDate = LocalDate.of(yearDebut, monthDebut, dayDebut);
        LocalDate endDate = LocalDate.of(yearFin, monthFin, dayFin);

        Semaine semaine = new Semaine(startDate,
                endDate,
                constraintRepository,
                journeeTypeProRepository,
                indisponibiliteRepository,
                rdvRepository);

        LocalDate startOfPreviousWeek = startDate.minusDays(6);
        LocalDate endOfPreviousWeek = endDate.minusDays(6);

        LocalDate startOfNextWeek = startDate.plusDays(6);
        LocalDate endOfNextWeek = endDate.plusDays(6);

        int dayDebutPreviousWeek = startOfPreviousWeek.getDayOfMonth();
        int monthDebutPreviousWeek = startOfPreviousWeek.getMonthValue();
        int yearDebutPreviousWeek = startOfPreviousWeek.getYear();
        int dayFinPreviousWeek = endOfPreviousWeek.getDayOfMonth();
        int monthFinPreviousWeek = endOfPreviousWeek.getMonthValue();
        int yearFinPreviousWeek = endOfPreviousWeek.getYear();

        int dayDebutNextWeek = startOfNextWeek.getDayOfMonth();
        int monthDebutNextWeek = startOfNextWeek.getMonthValue();
        int yearDebutNextWeek = startOfNextWeek.getYear();
        int dayFinNextWeek = endOfNextWeek.getDayOfMonth();
        int monthFinNextWeek = endOfNextWeek.getMonthValue();
        int yearFinNextWeek = endOfNextWeek.getYear();

        model.addAttribute("dayDebutPreviousWeek", dayDebutPreviousWeek);
        model.addAttribute("monthDebutPreviousWeek", monthDebutPreviousWeek);
        model.addAttribute("yearDebutPreviousWeek", yearDebutPreviousWeek);
        model.addAttribute("dayFinPreviousWeek", dayFinPreviousWeek);
        model.addAttribute("monthFinPreviousWeek", monthFinPreviousWeek);
        model.addAttribute("yearFinPreviousWeek", yearFinPreviousWeek);

        model.addAttribute("dayDebutNextWeek", dayDebutNextWeek);
        model.addAttribute("monthDebutNextWeek", monthDebutNextWeek);
        model.addAttribute("yearDebutNextWeek", yearDebutNextWeek);
        model.addAttribute("dayFinNextWeek", dayFinNextWeek);
        model.addAttribute("monthFinNextWeek", monthFinNextWeek);
        model.addAttribute("yearFinNextWeek", yearFinNextWeek);

        model.addAttribute("semaine", semaine);

        return "semaine";
    }

}
