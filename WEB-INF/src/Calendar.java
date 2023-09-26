import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Login.Client;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Calendrier")
public class Calendar extends HttpServlet {

    Client c;

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


         // Authentifie
        HttpSession session = req.getSession(true);
        c = (Client) session.getAttribute("client");
        if (c == null) {
            res.sendRedirect("Deconnect?Accueil");
        }


        PrintWriter out = res.getWriter();
        res.setContentType("text/html;");
        out.println( "<head><title>Table Ascii</title>" );
        out.println("<center>");
        out.println( "<h1>Calendrier</h1>" );

        Connection con = null;
        try {
            con = DriverManager.getConnection(
                    getServletContext().getInitParameter("url"),
                    getServletContext().getInitParameter("login"),
                    getServletContext().getInitParameter("password"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("erreur connection sql");
        }

        try {
            Statement stmt = con.createStatement();
            String query = "select * from rdv";
            ResultSet rs = stmt.executeQuery(query);
            out.println("<table>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>");
                out.println(rs.getObject("jour"));
                out.println(rs.getObject("heure"));
                out.println(rs.getInt("duree"));
                out.println(rs.getString("nbPersonneMax"));
                out.println(rs.getString("etat"));
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("erreur de requete sql");
        }



        try {
            out.println("<h1> Mes rendez vous du jour</h1>");
            Statement stmt1 = con.createStatement();
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
            ResultSet rs1 = stmt1.executeQuery(rdvToday);
            out.println("<table>");
            while (rs1.next()) {
                out.println("<tr>");
                out.println("<td>");
                out.println(rs1.getObject("rdv.heure"));
                out.println(rs1.getObject("client.nomC"));
                out.println(rs1.getObject("rdv.etat"));
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("erreur de requete sql");
        }

    }

}
