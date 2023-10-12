package fr.uniplanify;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Jour")
public class Jour extends HttpServlet{

    private int heureStart = 8;
    private int heureEnd = 18;
    private int dureeRDV = 15;

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();

        int day = Integer.parseInt(req.getParameter("day"));
        int month = Integer.parseInt(req.getParameter("month"));
        int year = Integer.parseInt(req.getParameter("year"));

        LocalDate dateSelectionnee = LocalDate.of(year,month,day);

        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\"><title>Jour</title>");
        out.println("<LINK rel=\"stylesheet\" type=\"text/css\" href=\"style/style.css\">");
        out.println("</head>");
        out.println("<body>");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH);
        String dateFormatee = dateSelectionnee.format(formatter);

        out.println("<table> <tr> <td>" + dateFormatee + "</td> </tr>");


        // Affichage des heures de la journée
        LocalTime heureDebut = LocalTime.of(heureStart, 0); // Heure de début (00:00)
        LocalTime heureFin = LocalTime.of(heureEnd, 00); // Heure de fin (23:59)
        LocalTime heureActuelle = heureDebut;

        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm");

        while (!heureActuelle.isAfter(heureFin)) {
            out.println("<tr><td><div class=\"cellule\"><div class=\"dayNumber\">");
            out.println(heureActuelle.format(heureFormatter));
            out.println("</div>");
            out.println("<div class=\"event\">");
            out.println("DISPO EN DUR</div></td>");
            out.println("</tr>");
            heureActuelle = heureActuelle.plusMinutes(dureeRDV); // Incrément de 30 minutes
        }

        out.println("</body>");
    }


}