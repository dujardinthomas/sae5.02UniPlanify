package Login;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        PrintWriter out = res.getWriter();
        out.println("<html><header><title>Login</title></header><body>");



        String mailC = req.getParameter("mail");
        String password = req.getParameter("password");

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
			out.println("connexion refusé");
			res.sendError(404, "connexion refusé !");
        }

        try {
            String requete = "select * from client where mailc = '" + mailC + "' and password = '" + password + "'";
            PreparedStatement ps = con.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            // si user connu on creer un objet client 
            if (rs.next()) {
                out.println("utilisateur connu, bienvenue !, redirection...");
                Client c = new Client(rs.getString("idC"),
                        rs.getString("nomC"),
                        rs.getString("prenomC"),
                        rs.getString("mailC"),
                        rs.getString("password"),
                        rs.getBoolean("pro"));

                System.out.println(c.getPro());
                HttpSession session = req.getSession(true);
                session.setAttribute("client", c);

                if(c.getPro() == true){
                    //C'EST UN PRO
                    res.sendRedirect("Pro");
                }else{
                    res.sendRedirect("Calendrier");
                }

                
            } else {
                out.println(
                        "<h1>utilisateur non reconnu !</h1> <br><h2>Assurez vous d'avoir entrer correctement vos informations de connexions !</h2>");
            }
        } catch (Exception e) {
            out.println("<h1>" + e.getMessage() + "</h1>");
        }

        out.println("</body>");
        out.println("<footer> <button> <a href=login.html>Se connecter</a></button></footer");
        out.println("<html>");



    }
    
}
