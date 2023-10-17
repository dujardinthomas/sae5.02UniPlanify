package fr.uniplanify.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.uniplanify.models.dao.ClientDAO;
import fr.uniplanify.models.dao.ConstraintsDAO;
import fr.uniplanify.models.dao.RdvClientDAO;
import fr.uniplanify.models.dao.RdvDAO;
import fr.uniplanify.models.dto.Client;
import fr.uniplanify.models.dto.Constraints;
import fr.uniplanify.models.dto.Rdv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Reserve")
public class Reserve extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // soit on fait passer les parametres en url ou en session
        int year = Integer.parseInt(req.getParameter("year"));
        int month = Integer.parseInt(req.getParameter("month"));
        int day = Integer.parseInt(req.getParameter("day"));
        int hours = Integer.parseInt(req.getParameter("hours"));
        int minute = Integer.parseInt(req.getParameter("minutes"));

        ClientDAO clDAO = new ClientDAO();
        Client user = clDAO.getClientByIdC(Integer.parseInt(req.getParameter("idC")));

        // HttpSession session = req.getSession(true);
        // Client user = (Client) session.getAttribute("user");

        LocalDate date = LocalDate.of(year, month, day);
        LocalTime time = LocalTime.of(hours, minute, 0);

        ConstraintsDAO cDAO = new ConstraintsDAO();
        int nbPersonne = cDAO.getConstraints().getNbPersonneMaxDefault();

        List<Client> clients = new ArrayList<>();
        clients.add(user);

        RdvDAO rdvDAO = new RdvDAO();
        Rdv rdvTrouve = rdvDAO.getRDVByDateAndHeure(date, time);

        res.setContentType("text/html; charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\"><title>Reservation</title>");
        out.println("<LINK rel=\"stylesheet\" type=\"text/css\" href=\"style/style.css\">");
        out.println("</head>");
        out.println("<body>");


        String etat = "reservé";
        if (nbPersonne > 1) {
            if (rdvTrouve != null) {
                // rdv a déja été reservé mais peut etre qu'on peut ajouter des gens
                if (rdvTrouve.getClients().size() < nbPersonne) {
                    // on peut ajouter un client
                    RdvClientDAO rdvClientDAO = new RdvClientDAO();
                    boolean statut = rdvClientDAO.createRdvClient(date, time, user.getIdC());
                    out.println("<h1>client " + clients.get(0).getIdC() + (statut ? " à été " : "n'a pas été ") + " ajouté au rendez-vous du " + date + " à " + time + "</h1>");
                }
                else{
                    out.println("<h1>Impossible de prendre un rendez-vous pour ce " + date + " à " + time + " !<h1>");
                    out.println("<h2>Il n'y a malheureusement plus de places disponible pour ce rendez-vous.</h2>");
                }
            } else {
                boolean statut = rdvDAO.createRDV(new Rdv(date, time, etat, clients));
                out.println("<h1>rendez-vous du " + date + " à " + time + (statut ? " à été crée " : "n'a pas été crée ") + " avec le client "+ clients.get(0).getIdC() +"</h1>");
            }
        } else {
            boolean statut = rdvDAO.createRDV(new Rdv(date, time, etat, clients));
            out.println("<h1>rendez-vous du " + date + " à " + time + (statut ? " à été crée " : "n'a pas été crée ") + " avec le client "+ clients.get(0).getIdC() +"</h1>");
        }

    }

}
