package fr.sae502.uniplanify;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ControlerCalendrier {

    @Autowired
    private JourneeTypeProRepository journeeTypeProRepository;

    @GetMapping("/calendrier")
    @ResponseBody
    public String calendrier(){
         return generateCalendarManuel(2021, 9);
        //return "BRAVO TOTO !!!!!!!!!! t'es le meilleur !!!!";
    }

    private String generateCalendarManuel(int year, int month) {

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        int daysInMonth = firstDayOfMonth.lengthOfMonth();
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());

        StringBuilder calendarHTML = new StringBuilder();
        calendarHTML.append("<table class=\"calendar\">");
        calendarHTML.append("<h1>" + monthName + " " + year + "</h1>");
        calendarHTML.append("<tr>");
        calendarHTML.append("<th>Lundi</th>");
        calendarHTML.append("<th>Mardi</th>");
        calendarHTML.append("<th>Mercredi</th>");
        calendarHTML.append("<th>Jeudi</th>");
        calendarHTML.append("<th>Vendredi</th>");
        calendarHTML.append("<th>Samedi</th>");
        calendarHTML.append("<th>Dimanche</th>");
        calendarHTML.append("</tr>");

        calendarHTML.append("<tr>");

        for (int i = 1; i < startDayOfWeek; i++) {
            // case vide pour faire un rectangle = lundi au 1er jour
            calendarHTML.append("<td></td>");
        }

        Iterable<JourneeTypePro> semainePro = journeeTypeProRepository.findAll();
        List<String> dayWork = new ArrayList<>();
        for (JourneeTypePro journee : semainePro) {
            dayWork.add(journee.getJour());
        }

        LocalDate currentDate = firstDayOfMonth;
        for (int day = 1; day <= daysInMonth; day++) {
            // chaque jour

            LocalDate now = LocalDate.of(year, month, day);
            String dayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRENCH);
            if (dayWork.contains(dayOfWeek.toString())) {
                // si ouvert lien cliquable
                calendarHTML.append("<td><div class=\"cellule\">");
                calendarHTML.append("<div class=\"dayNumber\">");
                calendarHTML
                        .append("<a href=Jour?day=" + day + "&month=" + month + "&year=" + year + ">" + day + "</a>");
            } else {
                calendarHTML.append("<td><div class=\"celluleClose\">");
                calendarHTML.append("<div class=\"dayNumber\">");
                calendarHTML.append(day);
            }

            calendarHTML.append("</div>");
            calendarHTML.append("</div></td>");

            if (currentDate.getDayOfWeek().getValue() == 7) {
                calendarHTML.append("</tr>");
                // Vérifier si nous avons encore des jours à ajouter
                if (day < daysInMonth) {
                    calendarHTML.append("<tr>");
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
