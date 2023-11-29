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

        String servletPath = ((HttpServletRequest) req).getServletPath();

        if (servletPath.equals("/Deconnect")) {
            // Filtre ne vas pas s'appliquer sur cette page !
            chain.doFilter(req, res);
        } else {

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
            if (cheminAccueil == null) {
                // mettre en requete dans jsp comme dans les servlet : flemme
                cheminAccueil = "";
            }
            if (pageTitle == null) {
                // mettre en requete dans jsp comme dans les servlet : flemme
                pageTitle = "UniPlanify";
            }
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
    }

    private String generateHeader(Client user, String pageTitle, String cheminAccueil, String contextPath) {

        // Déclaration du contenu CSS en ligne
        String cssContent = "body {\r\n" + //
                "    margin: 0;\r\n" + //
                "    padding: 0;\r\n" + //
                "    font-family: Arial, sans-serif;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                ".calendar {\r\n" + //
                "    width: 100%;\r\n" + //
                "    border-collapse: collapse;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                "td {\r\n" + //
                "    border: 1px solid #ccc;\r\n" + //
                "    padding: 0px;\r\n" + //
                "    text-align: center;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                ".cellule{\r\n" + //
                "    background-color: #a25e5e;\r\n" + //
                "    padding: 10px;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                ".celluleClose{\r\n" + //
                "    background-color: #696969;\r\n" + //
                "    padding: 10px;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                ".dayNumber {\r\n" + //
                "    padding: 10px;\r\n" + //
                "    font-weight: bold;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                ".event {\r\n" + //
                "    /* Personnalisez le style de votre texte centré ici */\r\n" + //
                "    padding: 10px;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                "\r\n" + //
                ".settingsCalendar{\r\n" + //
                "    position: fixed;\r\n" + //
                "    bottom: 30;\r\n" + //
                "    background-color: #a25e5e;\r\n" + //
                "    width: 90%;\r\n" + //
                "    text-align: center;\r\n" + //
                "    padding: 10px;\r\n" + //
                "\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                "\r\n" + //
                "\r\n" + //
                "footer {\r\n" + //
                "    position: fixed;\r\n" + //
                "    bottom: 0;\r\n" + //
                "    width: 100%;\r\n" + //
                "    background-color: #333;\r\n" + //
                "    color: #fff;\r\n" + //
                "    text-align: center;\r\n" + //
                "    padding: 10px 0;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                "\r\n" + //
                "\r\n" + //
                "body {\r\n" + //
                "    font-family: Tahoma, Arial, sans-serif;\r\n" + //
                "    background-color: lavender;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                "th {\r\n" + //
                "    color: white;\r\n" + //
                "    background-color: darkgray;\r\n" + //
                "    padding: 5px;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                "table,\r\n" + //
                "tr {\r\n" + //
                "    border: solid black 2px;\r\n" + //
                "    border-collapse: collapse;\r\n" + //
                "    text-align: center;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                "tr:nth-child(2n+1) {\r\n" + //
                "    background-color: lightgray;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                "tr:hover {\r\n" + //
                "    background-color: lightgreen;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                "\r\n" + //
                "\r\n" + //
                "\r\n" + //
                "form {\r\n" + //
                "  border: 3px solid #f1f1f1;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                ".container {\r\n" + //
                "  padding: 36px;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                "input[type=text], input[type=password] {\r\n" + //
                "  width: 100%;\r\n" + //
                "  padding: 12px 20px;\r\n" + //
                "  margin: 8px 0;\r\n" + //
                "  display: inline-block;\r\n" + //
                "  border: 1px solid #ccc;\r\n" + //
                "  box-sizing: border-box;\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                "\r\n" + //
                ""; // Votre CSS ici

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html lang=\"fr\">");
        sb.append("<header>");
        sb.append("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0\">");
        sb.append("<meta charset=\"UTF-8\">");
        sb.append("<title>");
        sb.append(pageTitle);
        sb.append("</title>");
        sb.append("<style>");
        sb.append(cssContent);
        sb.append("</style>");
        // sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + contextPath
        // + "/style/style.css\">");
        // sb.append("<LINK rel=\"stylesheet\" type=\"text/css\"
        // href=\"style/style.css\">");
        sb.append("</header>");
        sb.append("<body>");

        sb.append("<nav>");
        sb.append("<ul class=\"nav-menu\">");
        sb.append("<li><a href=" + cheminAccueil + "Calendrier + \">Accueil</a></li>");

        if (user != null && user.getPro()) {
            sb.append("<li><a href=" + cheminAccueil + "Pro>Mon espace pro</a></li>");
            sb.append("<li><a href=" + cheminAccueil + "Pro\\initialisation.jsp>Initialisation</a></li>");
            sb.append(
                    "<li><a href=" + cheminAccueil + "Pro\\indisponibilite.jsp>Ajouter des indisponibilités</a></li>");
        } else {
            sb.append("<li><a href=" + cheminAccueil + "Perso>Mon espace perso</a></li>");
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
            sb.append(" <button> <a href= " + cheminAccueil + "Deconnect>Se déconnecter</a></button> ");
        } else {
            sb.append(" <button> <a href=login.jsp>Se connecter</a> </button> ");
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
