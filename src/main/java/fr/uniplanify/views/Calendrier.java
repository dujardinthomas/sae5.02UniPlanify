package fr.uniplanify.views;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import fr.uniplanify.models.dto.JourneeTypePro;

@WebServlet("/Calendrier")
public class Calendrier extends HttpServlet {

    int year;
    int month;

    EntityManagerFactory emf;
    EntityManager em;

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // a mettre dans toutes les servlets !
        // on ecrit que l'interieur de la balise body le header, footer c'est dans
        // HeaderFooterFilter !!
        req.setAttribute("pageTitle", "Calendrier UniPlanity");
        req.setAttribute("cheminAccueil", "");

        emf = Persistence.createEntityManagerFactory("no-action-bdd");
        em = emf.createEntityManager();

        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();

        LocalDate dateActuelle = LocalDate.now();

        if (req.getParameter("month") != null) {
            year = Integer.parseInt(req.getParameter("year"));
            month = Integer.parseInt(req.getParameter("month"));
        } else {
            year = dateActuelle.getYear();
            month = dateActuelle.getMonthValue();
        }

        String calendarHTML = generateCalendarManuel(year, month);

        out.println(calendarHTML);

        // Gérer le cas du mois précédent
        int previousMonth = month - 1;
        int previousYear = year;
        if (previousMonth < 1) {
            previousMonth = 12;
            previousYear--;
        }

        // Gérer le cas du mois suivant
        int nextMonth = month + 1;
        int nextYear = year;
        if (nextMonth > 12) {
            nextMonth = 1;
            nextYear++;
        }

        out.println("<div class=\"settingsCalendar\">");
        out.println("<a href=\"?year=" + previousYear + "&month=" + previousMonth + "\">Mois précedent</a>");
        out.println("<a href=\"?year=" + dateActuelle.getYear() + "&month=" + dateActuelle.getMonth().getValue()
                + "\">Aujourd'hui</a>");
        out.println("<a href=\"?year=" + nextYear + "&month=" + nextMonth + "\">Mois suivant</a>");
        out.println("</div>");
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

        List<JourneeTypePro> semainePro = em.createNamedQuery("JourneeTypePro.findAll", JourneeTypePro.class)
                .getResultList();
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