package fr.uniplanify.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.uniplanify.models.dto.CleCompositeIndisponibilite;
import fr.uniplanify.models.dto.CleCompositeRDV;
import fr.uniplanify.models.dto.Constraints;
import fr.uniplanify.models.dto.Creneau;
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
import jakarta.servlet.http.HttpSession;

@WebServlet("/Jour")
public class ListeRDVJourController extends HttpServlet {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH);

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        int day = Integer.parseInt(req.getParameter("day"));
        int month = Integer.parseInt(req.getParameter("month"));
        int year = Integer.parseInt(req.getParameter("year"));

        LocalDate selectedDate = LocalDate.of(year, month, day);
        HttpSession session = req.getSession(true);
        session.setAttribute("selectedDate", selectedDate);
        session.setAttribute("selectedDateString", selectedDate.format(formatter));

        List<Rdv> allRdvToday = getRdvStatus(selectedDate);
        session.setAttribute("listRdvDay", allRdvToday);

        res.sendRedirect("jour.jsp");
    }

    public List<Rdv> getRdvStatus(LocalDate selectedDate) {
        List<Rdv> listRdvDay = new ArrayList<>();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("no-action-bdd");
        EntityManager em = emf.createEntityManager();

        // Récupération des contraintes
        Constraints constraints = em.createNamedQuery("Constraints.findAll", Constraints.class).getSingleResult();
        int nbPersonneMax = constraints.getNbPersonneMaxDefault();
        int dureeRDV = constraints.getDureeDefaultMinutes();

        String dayStringNumberMonthYear = selectedDate.format(formatter);
        JourneeTypePro dayTime = em.find(JourneeTypePro.class, dayStringNumberMonthYear.split(" ")[0]);

        // CleCompositeIndisponibilite cleCompositeIndisponibilite = new
        // CleCompositeIndisponibilite();
        // cleCompositeIndisponibilite.setDebutJour(selectedDate);
        // Indisponibilite indisponibilite = em.find(Indisponibilite.class,
        // selectedDate);

        String query = "SELECT * FROM indisponibilite WHERE " +
                "debutjour >= '" + selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "' " +
                "AND finjour <= '" + selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "'";

        List<Indisponibilite> indisponibilites = em.createNativeQuery(query, Indisponibilite.class).getResultList();

        if (dayTime == null) {
            System.out.println("jour fermé!");

        } else {

            for (Indisponibilite indispo : indisponibilites) {
                CleCompositeIndisponibilite i = indispo.getCleCompositeIndisponibilite();
                if (selectedDate.isAfter(i.getDebutJour())
                        || selectedDate.isEqual(i.getDebutJour())
                                && selectedDate.isBefore(i.getFinJour())
                        || selectedDate.isEqual(i.getFinJour())) {
                    System.out.println("jour fermé car indispo!");
                }
            }

            LocalTime startTimeDay = dayTime.getHeureDebut();
            LocalTime endTimeDay = dayTime.getHeureFin();
            LocalTime timeNow = startTimeDay;

            while (!timeNow.plusMinutes(dureeRDV).isAfter(endTimeDay)) {

                for (Indisponibilite indispo : indisponibilites) {
                    CleCompositeIndisponibilite i = indispo.getCleCompositeIndisponibilite();
                    if (timeNow.isAfter(i.getDebutHeure())
                            && timeNow.isBefore(i.getFinHeure())
                            || timeNow.equals(i.getDebutHeure())
                            || timeNow.equals(i.getFinHeure())) {
                        System.out.println("heure indispo!");
                        timeNow = timeNow.plusMinutes(dureeRDV);
                        continue;
                    }
                }

                CleCompositeRDV cleRDV = new CleCompositeRDV();
                cleRDV.setHeure(timeNow);
                cleRDV.setJour(selectedDate);
                Rdv rdvActuelle = em.find(Rdv.class, cleRDV);

                if (rdvActuelle == null) {
                    // Si aucun rendez-vous, la plage est disponible
                    rdvActuelle = new Rdv();
                    rdvActuelle.setCleCompositeRDV(cleRDV);
                    rdvActuelle.setEtat("DISPONIBLE POUR LE MOMENT ");
                } else if (rdvActuelle.getClients().size() < nbPersonneMax) {
                    // Si des places sont disponibles dans le rendez-vous
                    rdvActuelle.setEtat(
                            "ENCORE " + (nbPersonneMax - rdvActuelle.getClients().size()) + " PLACES DISPONIBLES SUR "
                                    + nbPersonneMax + " POUR LE MOMENT ");
                } else {
                    // Si le rendez-vous est complet
                    rdvActuelle.setEtat("COMPLET");
                }
                listRdvDay.add(rdvActuelle);
                timeNow = timeNow.plusMinutes(dureeRDV); // Incrément de la duree de rdv fixé par le pro
            }
        }
        return listRdvDay;
    }
}
