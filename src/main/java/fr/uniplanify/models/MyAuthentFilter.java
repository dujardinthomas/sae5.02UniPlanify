package fr.uniplanify.models;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebFilter;

// Attention : le pattern est recherché dans l'URL, pas dans le chemin unix
// Pour les JSP, le repertoire Unix est aussi cité dans l'URL, pas pour les Servlets !
@WebFilter(urlPatterns = {"/Reserve/*"})
public class MyAuthentFilter extends HttpFilter
{

    // Attention : HTTPFilter est introduit dans servlet4.0, donc Tomcat9.0
    // Avec le plugin tomcat7 il faut rester sur l'interface Filter
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException
    {
        System.out.println("Le filtre se déclenche");

        HttpSession session = req.getSession(true); 
        if (session.getAttribute("token")!=null)
        {           
            System.out.println("Le token est présent");
            chain.doFilter(req, res);
        }
        else
        {
                System.out.println("Le token n'est pas présent");
                // non authentifié, on range l'appel dans la session
                // et on  appelle la page de login
                session.setAttribute("origine",req.getRequestURI());
                res.sendRedirect(req.getContextPath()+"/login.jsp");
        }
    }

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
