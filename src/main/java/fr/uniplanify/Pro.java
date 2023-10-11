package fr.uniplanify;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.uniplanify.dao.DS;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Pro")
public class Pro extends HttpServlet {

    private DS ds = new DS();
	private Connection con;


    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // // Authentifie
        // HttpSession session = req.getSession(true);
        // c = (Client) session.getAttribute("client");
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

        con = ds.getConnection();
        

        try {
            out.println("<h1> Mes rendez vous</h1>");
            Statement stmt = con.createStatement();
            String rdvToday = "SELECT\n" + //
                    "  rdv.heure,\n" + //
                    "  client.nomC,\n" + //
                    "  rdv.etat\n" + //
                    "FROM\n" + //
                    "  rdv\n" + //
                    "INNER JOIN\n" + //
                    "  rdvClient\n" + //
                    "ON\n" + //
                    "  rdv.jour = rdvClient.jour\n" + //
                    "AND\n" + //
                    "  rdv.heure = rdvClient.heure\n" + //
                    "INNER JOIN\n" + //
                    "  client\n" + //
                    "ON\n" + //
                    "  rdvClient.idC = client.idC\n" + //
                    "WHERE\n" + //
                    "  rdv.jour = DATE( NOW());";

            String rdvAll = "SELECT\n" + //
                    "  rdv.jour,\n" + //
                    "  rdv.heure,\n" + //
                    "  client.nomC,\n" + //
                    "  client.prenomC,\n" + //
                    "  rdv.etat\n" + //
                    "FROM\n" + //
                    "  rdv\n" + //
                    "INNER JOIN\n" + //
                    "  rdvClient\n" + //
                    "ON\n" + //
                    "  rdv.jour = rdvClient.jour\n" + //
                    "AND\n" + //
                    "  rdv.heure = rdvClient.heure\n" + //
                    "INNER JOIN\n" + //
                    "  client\n" + //
                    "ON\n" + //
                    "  rdvClient.idC = client.idC;";

            ResultSet rs = stmt.executeQuery(rdvAll);
            out.println("<table>");
                out.println("<tr>");
                    out.println("<td>Jour</td>");
                    out.println("<td>Heure</td>");
                    out.println("<td>Nom</td>");
                    out.println("<td>Preom</td>");
                    out.println("<td>Etat du RDV</td>");
                out.println("</tr>");

            while (rs.next()) {
                out.println("<tr>");
                    out.println("<td>" + rs.getObject("jour") + "</td>");
                    out.println("<td>" + rs.getObject("heure") + "</td>");
                    out.println("<td>" + rs.getObject("nomC") + "</td>");
                    out.println("<td>" + rs.getObject("prenomC") + "</td>");
                    out.println("<td>" + rs.getObject("etat") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("erreur de requete sql");
        }

        out.println("</center>");
        out.println("</body>");
        out.println("<footer> <button> <a href=Deconnect>Se déconnecter</a></button></footer");
        out.println("</html>");
    }

}
