package fr.uniplanify.views;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import fr.uniplanify.models.dto.CleCompositeIndisponibilite;
import fr.uniplanify.models.dto.CleCompositeRDV;
import fr.uniplanify.models.dto.Constraints;
import fr.uniplanify.models.dto.Indisponibilite;
import fr.uniplanify.models.dto.JourneeTypePro;
import fr.uniplanify.models.dto.Rdv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Jour")
public class Jour extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //a mettre dans toutes les servlets !
        //on ecrit que l'interieur de la balise body le header, footer c'est dans HeaderFooterFilter !!
        req.setAttribute("pageTitle", "Choisir un jour - UniPlanity");
        req.setAttribute("cheminAccueil", "");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("no-action-bdd");
        EntityManager em = emf.createEntityManager();
        
        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();

        int day = Integer.parseInt(req.getParameter("day"));
        int month = Integer.parseInt(req.getParameter("month"));
        int year = Integer.parseInt(req.getParameter("year"));

        LocalDate selectedDate = LocalDate.of(year, month, day);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH);
        String dayStringNumberMonthYear = selectedDate.format(formatter);

        out.println("<table> <tr> <td>" + dayStringNumberMonthYear + "</td> </tr>");

        // Affichage des heures en fonction du jour
        JourneeTypePro dayTime = em.find(JourneeTypePro.class, dayStringNumberMonthYear.split(" ")[0]);// on recupere le 1er mot : lundi
        if (dayTime == null) {
            System.out.println("jour fermé!");
            out.println("<tr> <td>Fermé !</td> </tr>");

        } else {

            LocalTime startTimeDay = dayTime.getHeureDebut();
            LocalTime endTimeDay = dayTime.getHeureFin();
            LocalTime timeNow = startTimeDay;

            String priseRDV = "";

            DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm");
            Constraints constraints = em.createNamedQuery("Constraints.findAll", Constraints.class).getSingleResult();

            int dureeRDV = constraints.getDureeDefaultMinutes();
            System.out.println("duree rdv : " + dureeRDV);

            //CleCompositeIndisponibilite cle = new CleCompositeIndisponibilite(selectedDate, endTimeDay, selectedDate, timeNow)

            //Indisponibilite indispoExistant = em.find(Indisponibilite.class, ) 

            while (!timeNow.plusMinutes(dureeRDV).isAfter(endTimeDay)) {

                //IF INDISPO
                
                System.out.println(timeNow);
                // tant que il y a encore des creneaux
                String etat = "";
                String style = "background-color:#ffe694";

                CleCompositeRDV cleRDV = new CleCompositeRDV();
                cleRDV.setHeure(timeNow);
                cleRDV.setJour(selectedDate);
                Rdv rdv = em.find(Rdv.class, cleRDV);
                
                if (rdv == null) {
                    // si null = pas de rdv à l'heureActuelle , affiche la cellule en vert
                    etat = "DISPONIBLE POUR LE MOMENT";
                    style = "background-color:#1aff00";
                    priseRDV = " <a href=\"Perso/Reserve?year="+year+"&month="+month+"&day="+day+"&hours="+timeNow.getHour()+"&minutes="+timeNow.getMinute()+ "\">Prendre RDV </a>";
                }else if (rdv.getClients().size() < constraints.getNbPersonneMaxDefault()) {
                    // si reste de la place = présence d'un rdv mais possibilité d'ajouter dans rdvclient , affiche la cellule en orange
                    etat = "ENCORE " + (constraints.getNbPersonneMaxDefault() - rdv.getClients().size()) + " PLACES DISPONIBLE SUR " + constraints.getNbPersonneMaxDefault() + " POUR LE MOMENT ";
                    style = "background-color:#FFA500";
                    priseRDV = " <a href=\"Perso/Reserve?year="+year+"&month="+month+"&day="+day+"&hours="+timeNow.getHour()+"&minutes="+timeNow.getMinute()+ "\">Prendre RDV </a>";
                }
                 else {
                    //si quota atteint affice la cellule en rouge
                    etat = "PAS DISPONIBLE "+rdv.toString();
                    style = "background-color:#ff0000";
                    priseRDV = "";
                }

                out.println("<tr><td><div class=\"cellule\"style=\"" + style + "\"><div class=\"dayNumber\">");
                out.println(timeNow.format(heureFormatter));
                
                out.println("</div>");
                out.println("<div class=\"event\">");
                out.println(etat + priseRDV + "</div></td>");
                out.println("</tr>");

                timeNow = timeNow.plusMinutes(dureeRDV); // Incrément de la duree de rdv fixé par le pro
            }
        }
        
        out.println("</table>");
    }

}
