package fr.uniplanify.models.login;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import fr.uniplanify.models.dao.ClientDAO;
import fr.uniplanify.models.dto.Client;

// N'est appelée QUE par la page de login
@WebServlet("/verif")
public class Verif extends HttpServlet
{
  public void service( HttpServletRequest req, HttpServletResponse res ) 
       throws ServletException, IOException
    {
            String login = req.getParameter("login");
            String pwd = req.getParameter("pwd");

            ClientDAO c = new ClientDAO();
            Client client = c.getClientByMailAndPwd(login, pwd);
        
            System.out.println("Verif : "+login+" "+pwd);
            if (login != null && pwd != null && client!= null)
            {   // le login est correct, on fournit la page demandée
                HttpSession session=req.getSession(true);
                session.invalidate();
                session=req.getSession(true);
                session.setAttribute("clientDTO", client);
                System.out.println("bienvenue " + client.getPrenomC() + " " + client.getNomC());
                System.out.println("Redirection vers "+req.getParameter("origine"));
                String redirection = req.getParameter("origine");
                if(redirection == ""){
                    res.sendRedirect(getServletContext().getInitParameter("accueil"));
                }
                else{
                    res.sendRedirect(redirection);
                }
                 
            } else
            {
                // non authentifié, on repart au login
                res.sendRedirect("login.jsp?mess=login/mdp incorrect : "+login+" "+pwd);
            }
    }
}
