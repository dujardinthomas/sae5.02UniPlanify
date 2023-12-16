<%@ page import="java.util.*" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
<%@ page import="fr.sae502.uniplanify.models.Jour" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Choisir un créneau - UniPlanify</title>
    <link rel="stylesheet" href="../css/style.css">

</head>
<body>

    <header>
        <nav>
            <ul>
                <li><div class="logo"><a href="/"><img src="../img/logo.png" alt="Logo UniPlanify"></a></div></li>
                <li><a href="../perso">Mon espace</a></li>
                <li><a href="/">Calendrier</a></li>
                <li><a href="../contact">Contact</a></li>
                <li><a href="../deconnexion">Deconnexion</a></li>
            </ul>
        </nav>
    </header>

    <% 
        Jour today = (Jour) request.getAttribute("jour");
    %>

    <h2> Prendre un rendez-vous pour le <%=today.getTitle()%> </h2>

    <table>
    <%
    if (today.getOuvert() == false) {
    %>
        <p>La journée est actuellement fermée, aucun rendez-vous disponible.</p>
    <%
    } else {
        
        try {
            for (Rdv rdvNow : today.getRdvs()) { %>
                <tr>
                    <td>
                    <%
                    double pourcentage = rdvNow.getRemplissagePourcentage();
                    String couleur = "";
                    if(pourcentage == 0) {
                        couleur = "background-color: #00FF00"; //vert
                    } else if (pourcentage < 50) {
                        couleur = "background-color: #ADFF2F"; //vert clair
                    } else if (pourcentage < 70) {
                        couleur = "background-color: #FFFF00"; //jaune
                    } else if (pourcentage < 100) {
                        couleur = "background-color: #ff9100"; //orange
                    } else if (pourcentage == 100) {
                        couleur = "background-color: #FF0000"; //rouge
                    } else{
                        couleur = ""; //rien
                    }
                    %>
                        <div class="cellule" style="<%= couleur %>">
                            <div class="dayNumber"> 
                                <%= rdvNow.urlToStringTakeRdv("code:heureDuRdv") %>
                            </div>
                            <div class="event"> 
                                <%= rdvNow.getEtat() %> 
                                <%= rdvNow.getRemplissagePourcentage() %>%
                            </div>
                       </div> 
                    </td>
                </tr>
            <% } %>
        <% 
        } catch (Exception e) { %>
            <p>Une erreur s'est produite lors de la récupération de la journée</p>
        <% }
    } %>

</table>
    
</body>
</html>



















