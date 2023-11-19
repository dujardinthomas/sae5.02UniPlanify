package fr.uniplanify.views;

import fr.uniplanify.models.dto.Client;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


public class Menu{

    StringBuilder menu;

    String perso = "<li><a href=\"Perso\">Mon espace perso</a></li>";
    String pro = "<li><a href=\"Pro\">Mon espace pro</a></li>";

    public Menu(HttpServletRequest req){
        menu = new StringBuilder();
        menu.append("<nav>");
        menu.append("<ul class=\"nav-menu\">");
        menu.append("<li><a href=\"Calendrier\">Accueil</a></li>");

        HttpSession session = req.getSession(true);
        Client user = (Client) session.getAttribute("clientDTO");
        if (user != null && user.getPro()) {
            menu.append(pro);
            menu.append("<li><a href=\"Pro\\initialisation.html\">Initialisation</a></li>");
            menu.append("<li><a href=\"Pro\\indisponibilite.html\">Ajouter des indisponibilités</a></li>");
        }
        else{
            menu.append(perso);
        }
        menu.append("</ul>");
        menu.append("</nav>");        
    }

    @Override
    public String toString() {
        return menu.toString();
    }
    
    

}
