package fr.sae502.uniplanify.login;

import java.io.IOException;

import fr.sae502.uniplanify.models.Utilisateur;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebFilter;

// Attention : le pattern est recherché dans l'URL, pas dans le chemin unix
// Pour les JSP, le repertoire Unix est aussi cité dans l'URL, pas pour les Servlets !
@WebFilter(urlPatterns = { "/pro/*" })
public class AuthentFilterPro extends HttpFilter {

    // Attention : HTTPFilter est introduit dans servlet4.0, donc Tomcat9.0
    // Avec le plugin tomcat7 il faut rester sur l'interface Filter
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("Le filtre pro se déclenche");

        HttpSession session = req.getSession(true);
        Utilisateur user = (Utilisateur) session.getAttribute("userDTO");
        if ((user != null) && (user.isPro())){
            System.out.println("L'utilisateur est présent, et est admin, poursuite...");
            chain.doFilter(req, res);
        } else {
            System.out.println(user.getNom() + " pro: " + user.isPro());
            System.out.println("L'utilisateur n'est pas présent, ou n'est pas admin, on envoie sur login...");
            // non authentifié, on range l'appel dans la session
            // et on appelle la page de login

            StringBuffer requestURL = req.getRequestURL(); // Récupère l'URL de la requête
            String queryString = req.getQueryString(); // Récupère les paramètres de l'URL

            if (queryString != null) {
                requestURL.append("?").append(queryString); // Ajoute les paramètres à l'URL si présents
            }

            String completeURL = requestURL.toString(); // Obtient l'URL complète avec les paramètres

            session.setAttribute("origine", completeURL); // Stocke l'URL complet dans la session

            // session.setAttribute("origine", req.getQueryString());
            System.out.println("origine : " + completeURL);
            res.sendRedirect(req.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
