import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Calendar")
public class Calendar extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

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

    }

}
