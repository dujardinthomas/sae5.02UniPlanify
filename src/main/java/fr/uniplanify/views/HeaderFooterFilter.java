package fr.uniplanify.views;

import java.io.IOException;
import java.io.PrintWriter;

import fr.uniplanify.models.dto.Client;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//s'applique sur tout !!!!
//fournit tout sauf le body
@WebFilter(urlPatterns = { "/*" })
public class HeaderFooterFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("Le filtre entete/pied de page se déclenche");

        // Créer un HttpServletRequestWrapper pour intercepter la réponse
        CharResponseWrapper wrapper = new CharResponseWrapper((HttpServletResponse) res);
        System.out.println("wrapper crée, on chain.doFilter");
        // Passer la requête enveloppée à la chaîne de filtres ou à la servlet suivante
        chain.doFilter(req, wrapper);
        System.out.println("chain.doFilter effectué on modifie la reponse");

        // Modifier la réponse
        String servletResponse = wrapper.toString();
        String pageTitle = (String) req.getAttribute("pageTitle");
        String cheminAccueil = (String) req.getAttribute("cheminAccueil");
        HttpSession session = req.getSession(true);
        Client user = (Client) session.getAttribute("clientDTO");

        String headerContent = generateHeader(user, pageTitle, cheminAccueil, req.getContextPath());
        String footerContent = generatefooter(user, pageTitle, cheminAccueil);

        // // Modifier le contenu de la réponse en ajoutant l'en-tête et le pied de page
        String modifiedResponse = headerContent + servletResponse + footerContent;

        // // Modifier le contenu de la réponse en ajoutant l'en-tête et le pied de page
        // String modifiedResponse = servletResponse + footerContent;

        // Envoyer la réponse modifiée au client
        PrintWriter out = res.getWriter();
        out.write(modifiedResponse);

        // Écrire la réponse modifiée dans le flux de sortie d'origine
        // PrintWriter writer = res.getWriter();
        // writer.write(modifiedResponse);

        // // Écrivez le contenu modifié dans le flux de sortie approprié
        // if (res.getContentType() != null && res.getContentType().startsWith("text"))
        // {
        // res.setContentLength(-1);
        // res.setCharacterEncoding("UTF-8");
        // res.getWriter().write(modifiedResponse);
        // } else {
        // res.getOutputStream().write(modifiedResponse.getBytes());
        // }

        System.out.println("Le filtre entete/pied de page se termine");

    }

    private String generateHeader(Client user, String pageTitle, String cheminServlet, String contextPath) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html lang=\"fr\">");
        sb.append("<header>");
        sb.append("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0\">");
        sb.append("<meta charset=\"UTF-8\">");
        sb.append("<title>");
        sb.append(pageTitle);
        sb.append("</title>");
        //sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + contextPath + "/style/style.css\">");
        //sb.append("<LINK rel=\"stylesheet\" type=\"text/css\" href=\"style/style.css\">");
        sb.append("</header>");
        sb.append("<body>");

        sb.append("<nav>");
        sb.append("<ul class=\"nav-menu\">");
        sb.append("<li><a href=\"Calendrier\">Accueil</a></li>");

        if (user != null && user.getPro()) {
            sb.append("<li><a href=\"Pro\">Mon espace pro</a></li>");
            sb.append("<li><a href=\"Pro\\initialisation.html\">Initialisation</a></li>");
            sb.append("<li><a href=\"Pro\\indisponibilite.html\">Ajouter des indisponibilités</a></li>");
        } else {
            sb.append("<li><a href=\"Perso\">Mon espace perso</a></li>");
        }
        sb.append("</ul>");
        sb.append("</nav>");

        return sb.toString();
    }

    private String generatefooter(Client user, String pageTitle, String cheminAccueil) {
        StringBuilder sb = new StringBuilder();
        sb.append("</body>");
        sb.append("<footer>");

        sb.append("<button> <a href=" + cheminAccueil + "Calendrier" + ">" + "Accueil" + "</a></button>");
        if (user != null) {
            sb.append("Bienvenue " + user.getPrenomC() + " !");
            sb.append(" <button> <a href= " + cheminAccueil + "Deconnect>Se déconnecter</a> <button> ");
        } else {
            sb.append(" <button> <a href=login.jsp>Se connecter</a> <button> ");
        }

        sb.append("ca vient du filtre !");
        sb.append("</footer>");
        sb.append("</html>");
        return sb.toString();
    }

    @Override
    public void destroy() {
        // Nettoyage des ressources
    }

}
