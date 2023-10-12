package fr.uniplanify;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@WebServlet("/CalendrierCount")
public class CalendrierCount extends HttpServlet {

    int year;
    int month; 

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        // // VERIFIE AUTENFIFIE
        // HttpSession session = req.getSession(true);
        // Client c = (Client) session.getAttribute("client");
        // if (c == null || c.getPro() == true) {
        // res.sendRedirect("Deconnect");
        // }


        String nameSession = "counters" + year + month; 

        HttpSession session = req.getSession(true);
        Map<String, Integer> counters = (Map<String, Integer>) session.getAttribute(nameSession);

        if (counters == null) {
            counters = new HashMap<>();
            session.setAttribute(nameSession, counters);
        }


        String day = req.getParameter("day");
        if (day != null) {
            Integer counter = counters.get(day);
            if (counter == null) {
                counter = 1;
            } else {
                counter++;
            }
            counters.put(day, counter);
        }


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

        String calendarHTML = generateCalendarManuel(year, month, counters);

        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\"><title>Calendrier</title>");
        out.println("<LINK rel=\"stylesheet\" type=\"text/css\" href=\"style/style.css\">");
        out.println("</head>");
        out.println("<body>");
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
        out.println("<a href=\"?year=" + dateActuelle.getYear() + "&month=" + dateActuelle.getMonth().getValue() + "\">Aujourd'hui</a>");
        out.println("<a href=\"?year=" + nextYear + "&month=" + nextMonth + "\">Mois suivant</a>");
        out.println("</div>");
        out.println("</body>");

        out.println("<footer> <button> <a href=Deconnect>Se déconnecter</a></button></footer");
        out.println("</html>");
    }


 
    private String generateCalendarManuel(int year, int month, Map<String, Integer> counters) {

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        int daysInMonth = firstDayOfMonth.lengthOfMonth();
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());

        StringBuilder calendarHTML = new StringBuilder();
        calendarHTML.append("<table class=\"calendar\">");
        calendarHTML.append("<h1>" + monthName + " " + year + "</h1>");
        // Ajouter l'en-tête du calendrier avec les jours de la semaine
        calendarHTML.append("<tr>");
        calendarHTML.append("<th>Lundi</th>");
        calendarHTML.append("<th>Mardi</th>");
        calendarHTML.append("<th>Mercredi</th>");
        calendarHTML.append("<th>Jeudi</th>");
        calendarHTML.append("<th>Vendredi</th>");
        calendarHTML.append("<th>Samedi</th>");
        calendarHTML.append("<th>Dimanche</th>");
        calendarHTML.append("</tr>");

        // Ajouter les lignes pour les jours du calendrier
        calendarHTML.append("<tr>");

        // Ajouter des cellules vides pour les jours précédant le premier jour du mois
        for (int i = 1; i < startDayOfWeek; i++) {
            calendarHTML.append("<td></td>");
        }

        LocalDate currentDate = firstDayOfMonth;
        for (int day = 1; day <= daysInMonth; day++) {
            // Ajouter une cellule pour le jour courant
            calendarHTML.append("<td><div class=\"cellule\">");

            calendarHTML.append("<div class=\"dayNumber\">");
            calendarHTML.append(day);
            calendarHTML.append("</div>");

            String dayStr = String.valueOf(day);
            Integer counter = counters.get(dayStr);
            counter = (counter == null) ? 0 : counter;

            calendarHTML.append("<div class=\"event\">");
            calendarHTML.append("<a href=\"?year=" + year + "&month=" + month + "&day=").append(dayStr).append("\">" + counter +"</a>");
            calendarHTML.append("</div>");
            calendarHTML.append("</div></td>");

            // Passer à la prochaine ligne chaque fois que nous atteignons le Dimanche (jour
            // 7)
            if (currentDate.getDayOfWeek().getValue() == 7) {
                calendarHTML.append("</tr>");
                // Vérifier si nous avons encore des jours à ajouter
                if (day < daysInMonth) {
                    calendarHTML.append("<tr>");
                }
            }
            currentDate = currentDate.plusDays(1);
        }

        // Ajouter des cellules vides pour les jours suivant le dernier jour du mois
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