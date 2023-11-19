package fr.uniplanify.views;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import fr.uniplanify.models.dto.Client;
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

@WebServlet("/Pro")
public class Pro extends HttpServlet {
    
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //a mettre dans toutes les servlets !
        //on ecrit que l'interieur de la balise body le header, footer c'est dans HeaderFooterFilter !!
        req.setAttribute("pageTitle", "Espace Pro - UniPlanity");
        req.setAttribute("cheminAccueil", "");

        HttpSession session = req.getSession(true);
        Client c = (Client) session.getAttribute("clientDTO");
        PrintWriter out = res.getWriter();
        res.setContentType("text/html; charset=UTF-8");
    
        out.println("<center>");

        out.println("<h1> MES RENDEZ-VOUS PROFESSIONELS</h1>");


        out.println("<table>");
        out.println("<tr>");
        out.println("<td>Jour</td>");
        out.println("<td>Heure</td>");
        out.println("<td>Nombre de personne</td>");
        out.println("<td>Nom</td>");
        out.println("<td>Prenom</td>");
        out.println("<td>Dernière modification</td>");
        out.println("</tr>");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("no-action-bdd");
        EntityManager em = emf.createEntityManager();

        List<Rdv> myRDVS = new ArrayList<>();
        myRDVS = em.createNamedQuery("Rdv.findAll", Rdv.class).getResultList();

        for (Rdv rdv : myRDVS) {
            out.println("<tr>");
            
            out.println("<td>" + rdv.getCleCompositeRDV().getJour() + "</td>");
            out.println("<td>" + rdv.getCleCompositeRDV().getHeure() + "</td>");
            out.println("<td>" + rdv.getClients().size() + "</td>");
            out.println("<td><table>");
            for (Client client : rdv.getClients()) {
                out.println("<tr>");
                out.println("<td>" + client.getNomC() + "</td>");
                out.println("</tr>");
            }
            out.println("</table></td>");

            out.println("<td><table>");
            for (Client client : rdv.getClients()) {
                out.println("<tr>");
                out.println("<td>" + client.getPrenomC() + "</td>");
                out.println("</tr>");
            }
            out.println("</table></td>");
            out.println("<td>" + rdv.getEtat() + "</td>");
            out.println("<tr>");
        }
        out.println("</table>");
    }

}
