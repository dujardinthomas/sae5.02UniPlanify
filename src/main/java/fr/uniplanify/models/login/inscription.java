package fr.uniplanify.models.login;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import fr.uniplanify.models.dto.Client;

// N'est appelée QUE par la page de login
@WebServlet("/inscription")
public class inscription extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("no-action-bdd");
        EntityManager em = emf.createEntityManager();

        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String login = req.getParameter("login");
        String pwd = req.getParameter("pwd");

        Client c = new Client();
        c.setNomC(nom);
        c.setPrenomC(prenom);
        c.setPro(false);
        c.setMailC(login);
        c.setPassword(pwd);

        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();

        boolean estDansLaBase = em.contains(c);

        if (estDansLaBase) { // compte créé

            System.out.println("compte créé");
            res.sendRedirect("login.jsp?mess=Votre compte à été créé avec le login" + login);
        } else {
            // non authentifié, on repart au login
            res.sendRedirect("login.jsp?mess=impossible de créer votre compte " + login);
        }
    }
}
