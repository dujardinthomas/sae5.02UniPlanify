package fr.uniplanify.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.uniplanify.models.dto.CleCompositeRDV;
import fr.uniplanify.models.dto.Client;
import fr.uniplanify.models.dto.Constraints;
import fr.uniplanify.models.dto.Rdv;
import fr.uniplanify.views.Footer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Perso/Reserve")
public class Reserve extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // soit on fait passer les parametres en url ou en session
        int year = Integer.parseInt(req.getParameter("year"));
        int month = Integer.parseInt(req.getParameter("month"));
        int day = Integer.parseInt(req.getParameter("day"));
        int hours = Integer.parseInt(req.getParameter("hours"));
        int minute = Integer.parseInt(req.getParameter("minutes"));

        HttpSession session = req.getSession(true);
        Client client = (Client) session.getAttribute("clientDTO");

        LocalDate dateDuRdv = LocalDate.of(year, month, day);
        LocalTime heureDuRdv = LocalTime.of(hours, minute, 0);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("no-action-bdd");
        EntityManager em = emf.createEntityManager();

        Constraints constraints = em.createNamedQuery("Constraints.findAll", Constraints.class).getSingleResult();
        int nbPersonne = constraints.getNbPersonneMaxDefault();

        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\"><title>Reservation</title>");
        out.println("<LINK rel=\"stylesheet\" type=\"text/css\" href=\"../style/style.css\">");
        out.println("</head>");
        out.println("<body>");

        LocalDateTime heureActuelle = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yy");
        String jjMMyy = heureActuelle.format(format);
        String etat = "reservé le " + jjMMyy;

        CleCompositeRDV cleRDV = new CleCompositeRDV();
        cleRDV.setHeure(heureDuRdv);
        cleRDV.setJour(dateDuRdv);
        Rdv rdvExistant = em.find(Rdv.class, cleRDV);

        if (rdvExistant != null) {
            // rdv a déja été reservé mais peut etre qu'on peut ajouter des gens
            if (rdvExistant.getClients().size() < nbPersonne) {
                // on peut ajouter un client

                rdvExistant.addClient(client);
                rdvExistant.setEtat(client.getNomC() + " ajouté le " + jjMMyy);
                em.getTransaction().begin();
                em.persist(rdvExistant);
                em.getTransaction().commit();

                boolean statut = em.contains(rdvExistant);

                out.println("<h1>client " + client.getIdC() + (statut ? " à été " : "n'a pas été ")
                        + " ajouté au rendez-vous du " + dateDuRdv + " à " + heureDuRdv + "</h1>");

            } else {
                out.println("<h1>Impossible de prendre un rendez-vous pour ce " + dateDuRdv + " à " + heureDuRdv
                        + " !<h1>");
                out.println("<h2>Il n'y a malheureusement plus de places disponible pour ce rendez-vous.</h2>");
            }
        } else {

            Rdv nouveauRdv = new Rdv();
            nouveauRdv.setCleCompositeRDV(cleRDV);
            nouveauRdv.addClient(client);
            nouveauRdv.setEtat(etat);

            em.getTransaction().begin();
            em.persist(nouveauRdv);
            em.getTransaction().commit();

            boolean statut = em.contains(nouveauRdv);

            out.println(
                    "<h1>rendez-vous du " + dateDuRdv + " à " + heureDuRdv
                            + (statut ? " à été crée " : "n'a pas été crée ")
                            + " avec le client " + client.getIdC() + "</h1>");
        }

        String monEspace = "Perso";
        if (client.getPro() == true) {
            monEspace = "Pro";
        }
        out.println("<h2><a href=\"../" + monEspace + "\">Accéder à mon espace</a></h2>");

        Footer footer = new Footer(req, "../");
        out.println(footer.toString());
        out.println("</body>");
        out.println("</html>");
    }

}
