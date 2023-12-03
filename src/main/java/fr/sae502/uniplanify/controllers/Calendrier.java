package fr.sae502.uniplanify.controllers;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.models.JourneeTypePro;
import fr.sae502.uniplanify.repository.JourneeTypeProRepository;

@Component
@Controller
public class Calendrier {

    private JourneeTypeProRepository journeeTypeProRepository;

    public Calendrier(JourneeTypeProRepository journeeTypeProRepository) {
        this.journeeTypeProRepository = journeeTypeProRepository;
    }

    @RequestMapping(value = "/")
    @Transactional
    public ModelAndView calendrier(@RequestParam(defaultValue = "0") int year, 
                                    @RequestParam(defaultValue = "0") int month) {

        LocalDate currentDate = LocalDate.now();
        if (month == 0) {
            year = currentDate.getYear();
            month = currentDate.getMonthValue();
        }
        String calendarHTML = generateCalendarManuel(year, month);

        // Gestion des mois précédents et suivants
        int previousMonth = month == 1 ? 12 : month - 1;
        int previousYear = month == 1 ? year - 1 : year;
        
        int nextMonth = month == 12 ? 1 : month + 1;
        int nextYear = month == 12 ? year + 1 : year;

        ModelAndView mav = new ModelAndView("calendrier");
        mav.addObject("calendrierHTML", calendarHTML);
        mav.addObject("previousYear", previousYear);
        mav.addObject("previousMonth", previousMonth);
        mav.addObject("nextYear", nextYear);
        mav.addObject("nextMonth", nextMonth);

        return mav;
    }

    private String generateCalendarManuel(int year, int month) {

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        int daysInMonth = firstDayOfMonth.lengthOfMonth();
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());

        StringBuilder calendarHTML = new StringBuilder();
        calendarHTML.append("<h1>" + monthName + " " + year + "</h1>");
        calendarHTML.append("<table class=\"calendar\">");
        calendarHTML.append("<tr>");
        calendarHTML.append("<th>Lundi</th>");
        calendarHTML.append("<th>Mardi</th>");
        calendarHTML.append("<th>Mercredi</th>");
        calendarHTML.append("<th>Jeudi</th>");
        calendarHTML.append("<th>Vendredi</th>");
        calendarHTML.append("<th>Samedi</th>");
        calendarHTML.append("<th>Dimanche</th>");
        calendarHTML.append("</tr>");

        calendarHTML.append("\n<tr>");

        for (int i = 1; i < startDayOfWeek; i++) {
            // case vide pour faire un rectangle = lundi au 1er jour
            calendarHTML.append("<td></td>");
        }

        Iterable<JourneeTypePro> semainePro = journeeTypeProRepository.findAll();
        List<String> dayWork = new ArrayList<>();
        for (JourneeTypePro journee : semainePro) {
            dayWork.add(journee.getJour());
            System.out.println(journee.getJour());
        }

        LocalDate currentDate = firstDayOfMonth;
        for (int day = 1; day <= daysInMonth; day++) {
            // chaque jour

            LocalDate now = LocalDate.of(year, month, day);
            String dayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRENCH);
            if (dayWork.contains(dayOfWeek.toString())) {
                // si ouvert lien cliquable
                calendarHTML.append("\n<td><div class=\"cellule\">");
                calendarHTML.append("<div class=\"dayNumber\">");
                calendarHTML
                        .append("<a href=\"Jour?day=" + day + "&month=" + month + "&year=" + year + "\">" + day + "</a>");
            } else {
                calendarHTML.append("\n<td><div class=\"celluleClose\">");
                calendarHTML.append("<div class=\"dayNumber\">");
                calendarHTML.append(day);
            }

            calendarHTML.append("</div>");
            calendarHTML.append("</div></td>");

            if (currentDate.getDayOfWeek().getValue() == 7) {
                calendarHTML.append("</tr>");
                // Vérifier si nous avons encore des jours à ajouter
                if (day < daysInMonth) {
                    calendarHTML.append("\n<tr>");
                }
            }
            currentDate = currentDate.plusDays(1);
        }

        // //case vide pour faire un rectangle = dernier jour au dimanche
        int lastDayOfWeek = currentDate.minusDays(1).getDayOfWeek().getValue();
        if (lastDayOfWeek != 7) {
            for (int i = lastDayOfWeek + 1; i <= 7; i++) {
                calendarHTML.append("<td></td>");
            }
        }

        calendarHTML.append("</tr>");
        calendarHTML.append("</table>");

        return calendarHTML.toString();
    }

}
