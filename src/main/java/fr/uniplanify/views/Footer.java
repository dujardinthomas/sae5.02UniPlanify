package fr.uniplanify.views;

import fr.uniplanify.models.dto.Client;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


public class Footer{

    String bienvenuePrenom = "";
    String buttonDeconnectConnect = "";
    String accueil = "";
    String pageAccueil = "Calendrier";

    public Footer(HttpServletRequest req, String cheminAccueil){
        accueil = "<button> <a href=" + cheminAccueil + pageAccueil + ">"+ "Accueil" + "</a></button>";
        HttpSession session = req.getSession(true);
        Client user = (Client) session.getAttribute("clientDTO");
        if (user != null) {
            bienvenuePrenom = "Bienvenue " + user.getPrenomC() + " !";
            buttonDeconnectConnect = " <button> <a href= " + cheminAccueil + "Deconnect>Se déconnecter</a> <button> ";
        }
        else{
            buttonDeconnectConnect = " <button> <a href=login.jsp>Se connecter</a> <button> ";
        }
    }

    public String getBienvenuePrenom() {
        return bienvenuePrenom;
    }

    public String getButtonDeconnectConnect() {
        return buttonDeconnectConnect;
    }

    public String getAccueil() {
        return accueil;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<footer>");
        sb.append(bienvenuePrenom);
        sb.append(buttonDeconnectConnect);
        sb.append(accueil);
        sb.append("</footer>");
        return sb.toString();
    }
    
    

}
