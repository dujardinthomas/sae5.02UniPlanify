package fr.uniplanify.models.login;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Deconnect")
public class Deconnect extends HttpServlet{

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        session.invalidate();
        String accueil = getServletContext().getInitParameter("accueil");
        System.out.println(accueil);
        res.sendRedirect(accueil);
    }

}