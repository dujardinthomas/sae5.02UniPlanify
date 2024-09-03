<%@ page import="java.util.*" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
<%@ page import="fr.sae502.uniplanify.view.Daily" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Choisir un créneau - UniPlanify</title>
    <link rel="icon" href="../img/logo.png" type="image/png">
    <link rel="stylesheet" href="../css/style.css">

</head>
<body>

<%@ include file="/WEB-INF/jsp/template/menu.jsp" %>

    <% 
        Daily today = (Daily) request.getAttribute("jour");
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
            for (Rdv rdvNow : today.getRdvs()) { 
                 if(rdvNow.isOuvert() == true) { %>
                <tr>
                    <td>
                    <%
                    double pourcentage = rdvNow.getFillPercentage();
                    String couleur = "";
                    if(pourcentage == 0) {
                        couleur = "background-color: #00FF00"; //vert
                    } else if (pourcentage < 50) {
                        couleur = "background-color: #ADFF2F"; //vert clair
                    } else if (pourcentage < 70) {
                        couleur = "background-color: #ff9600"; //orange clair
                    } else if (pourcentage < 100) {
                        couleur = "background-color: #ff6700"; //orange foncé
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
                                <%= rdvNow.getState() %> 
                                <%= rdvNow.getFillPercentage() %>%
                            </div>
                       </div> 
                    </td>
                </tr>
            <% }
            } %>
        <% 
        } catch (Exception e) { %>
            <p>Une erreur s'est produite lors de la récupération de la journée</p>
        <% }
    } %>

</table>
    
</body>
<%@ include file="/WEB-INF/jsp/template/footer.jsp" %>
</html>



















