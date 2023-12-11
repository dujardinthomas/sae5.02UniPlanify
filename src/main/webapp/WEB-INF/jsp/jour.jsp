<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
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
        <div class="logo">
            <img src="img/logo.png" alt="Logo UniPlanify">
        </div>
        <nav>
            <ul>
                <li><a href="../perso">Mon espace</a></li>
                <li><a href="/">Calendrier</a></li>
                <li><a href="../contact">Contact</a></li>
            </ul>
        </nav>
    </header>

    <% 

List<Rdv> today = (List<Rdv>) request.getAttribute("listRDV");
if(today == null){
    today = new ArrayList<Rdv>();
}

%>

<table>
    <tr>
        <td>${selectedDate}</td>
    </tr>

    <%
    if (today.isEmpty()) {
    %>
        <p>La journée est actuellement fermée, aucun rendez-vous disponible.</p>
    <%
    } else {
        try {
            for (Rdv rdvNow : today) { %>
                <tr>
                    <td>
                        <%= rdvNow.toStringJour() %>
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


















