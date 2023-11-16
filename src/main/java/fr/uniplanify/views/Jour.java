package fr.uniplanify.views;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import fr.uniplanify.models.dao.ConstraintsDAO;
import fr.uniplanify.models.dao.RdvDAO;
import fr.uniplanify.models.dao.SemaineTypeProDAO;
import fr.uniplanify.models.dto.Constraints;
import fr.uniplanify.models.dto.Rdv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Jour")
public class Jour extends HttpServlet {

    ConstraintsDAO cDAO = new ConstraintsDAO();
    

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();

        int day = Integer.parseInt(req.getParameter("day"));
        int month = Integer.parseInt(req.getParameter("month"));
        int year = Integer.parseInt(req.getParameter("year"));

        LocalDate dateSelectionnee = LocalDate.of(year, month, day);

        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\"><title>Jour</title>");
        out.println("<LINK rel=\"stylesheet\" type=\"text/css\" href=\"style/style.css\">");
        out.println("</head>");
        out.println("<body>");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH);
        String dateFormatee = dateSelectionnee.format(formatter);

        out.println("<table> <tr> <td>" + dateFormatee + "</td> </tr>");

        // Affichage des heures en fonction du jou
        SemaineTypeProDAO semDAO = new SemaineTypeProDAO();
        List<LocalTime> dayTime = semDAO.getDayPro(dateFormatee.split(" ")[0]); // on recupere le 1er mot : lundi
        if (dayTime == null) {
            System.out.println("jour fermé!");
            out.println("<tr> <td>Fermé !</td> </tr>");

        } else {

            LocalTime heureDebut = dayTime.get(0);
            LocalTime heureFin = dayTime.get(1);
            LocalTime heureActuelle = heureDebut;

            String priseRDV = "";

            DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm");
            Constraints constraints = cDAO.getConstraints();
            RdvDAO rDAO = new RdvDAO();

            int dureeRDV = cDAO.getConstraints().getDureeDefaultMinutes();
            System.out.println("duree rdv : " + dureeRDV);

            while (!heureActuelle.plusMinutes(dureeRDV).isAfter(heureFin)) {
                // tant que il y a encore des creneaux
                String etat = "";
                String style = "background-color:ffe694";

                Rdv rdv = rDAO.getRDVByDateAndHeure(dateSelectionnee, heureActuelle);
                

                if (rdv == null) {
                    // si null = pas de rdv à l'heureActuelle , affiche la cellule en vert
                    etat = "DISPONIBLE POUR LE MOMENT";
                    style = "background-color:1aff00";
                    priseRDV = " <a href=\"Reserve?year="+year+"&month="+month+"&day="+day+"&hours="+heureActuelle.getHour()+"&minutes="+heureActuelle.getMinute()+ "\">Prendre RDV </a>";
                }else if (rdv.getClients().size() < constraints.getNbPersonneMaxDefault()) {
                    // si reste de la place = présence d'un rdv mais possibilité d'ajouter dans rdvclient , affiche la cellule en orange
                    etat = "ENCORE " + (constraints.getNbPersonneMaxDefault() - rdv.getClients().size()) + " PLACES DISPONIBLE SUR " + constraints.getNbPersonneMaxDefault() + " POUR LE MOMENT ";
                    style = "background-color:FFA500";
                    priseRDV = " <a href=\"Reserve?year="+year+"&month="+month+"&day="+day+"&hours="+heureActuelle.getHour()+"&minutes="+heureActuelle.getMinute()+ "\">Prendre RDV </a>";
                }
                 else {
                    //si quota atteint affice la cellule en rouge
                    etat = "PAS DISPONIBLE "+rdv.toString();
                    style = "background-color:ff0000";
                    priseRDV = "";
                }

                out.println("<tr><td><div class=\"cellule\"style=\"" + style + "\"><div class=\"dayNumber\">");
                out.println(heureActuelle.format(heureFormatter));

                System.out.println(heureActuelle);
                
                out.println("</div>");
                out.println("<div class=\"event\">");
                out.println(etat + priseRDV + "</div></td>");
                out.println("</tr>");

                heureActuelle = heureActuelle.plusMinutes(dureeRDV); // Incrément de la duree de rdv fixé par le pro
            }

        }
        
        out.println("</table>");
        out.println("</body>");
    }

}
