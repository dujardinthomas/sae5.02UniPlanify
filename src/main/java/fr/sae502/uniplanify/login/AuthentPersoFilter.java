package fr.sae502.uniplanify.login;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import fr.sae502.uniplanify.SessionBean;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebFilter;


@WebFilter(urlPatterns = { "/perso/*" })
public class AuthentPersoFilter extends HttpFilter {

    @Autowired
    private SessionBean sessionBean;

    // Attention : HTTPFilter est introduit dans servlet4.0, donc Tomcat9.0
    // Avec le plugin tomcat7 il faut rester sur l'interface Filter
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("Le filtre se déclenche");

        // HttpSession session = req.getSession(true);
        
                

        // if (session.getAttribute("clientDTO") != null) {
        if (sessionBean.getUtilisateur() != null) {
            System.out.println("client : " + sessionBean.getUtilisateur());
            System.out.println("Le client est présent, poursuite...");
            chain.doFilter(req, res);
        } else {
            System.out.println("client : " + sessionBean.getUtilisateur());
            System.out.println("Le client n'est pas présent, on envoie sur login...");
            // non authentifié, on range l'appel dans la session
            // et on appelle la page de login

            // StringBuffer requestURL = req.getRequestURL(); // Récupère l'URL de la requête
            String requestURI = req.getRequestURI(); // Récupère l'URI de la requête
            System.out.println("URI : " + requestURI);
            String queryString = req.getQueryString(); // Récupère les paramètres de l'URL
            // System.out.println("query : " + queryString);

            if (queryString != null) {
                // requestURL.append("?").append(queryString); // Ajoute les paramètres à l'URL si présents
                requestURI += "?" + queryString;
            }

            // String completeURL = requestURL.toString(); // Obtient l'URL complète avec les paramètres

            // session.setAttribute("origine", completeURL); // Stocke l'URL complet dans la session

            // session.setAttribute("origine", requestURI);
            sessionBean.setOrigine(requestURI);
            System.out.println("origine : " + requestURI);
            res.sendRedirect("/login");
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
