package fr.uniplanify.views;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import fr.uniplanify.models.dao.RdvDAO;
import fr.uniplanify.models.dto.Client;
import fr.uniplanify.models.dto.Rdv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Perso")
public class Perso extends HttpServlet {

    // private DS ds = new DS();
	// private Connection con;

    RdvDAO r = new RdvDAO();
    


    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        Client c = (Client) session.getAttribute("clientDTO");
        List<Rdv> getAllMyRDV = r.getAllMyRDV(c);
        // // Authentifie
        //HttpSession session = req.getSession(true);
        //c = (Client) session.getAttribute("client");
        // if (c == null || c.getPro() == false) {
        // res.sendRedirect("Deconnect");
        // }

        PrintWriter out = res.getWriter();
        res.setContentType("text/html; charset=UTF-8");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\"><title>Espace Pro</title>");
        out.println("<LINK rel=\"stylesheet\" type=\"text/css\" href=\"style/style.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<center>");

        out.println("<h1> MES RENDEZ-VOUS CLIENT</h1>");


        out.println("<table>");
        out.println("<tr>");
        out.println("<td>Jour</td>");
        out.println("<td>Heure</td>");
        out.println("<td>Nombre de personne</td>");
        out.println("<td>Nom</td>");
        out.println("<td>Prenom</td>");
        out.println("</tr>");
        for (Rdv rdv : getAllMyRDV) {
            out.println("<tr>");
            
            out.println("<td>" + rdv.getJour() + "</td>");
            out.println("<td>" + rdv.getHeure() + "</td>");
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

            out.println("<tr>");
        }
        out.println("</table>");

        Footer footer = new Footer(req, "");
        out.println(footer.toString());
        out.println("</body>");
        out.println("</html>");
    }

}
