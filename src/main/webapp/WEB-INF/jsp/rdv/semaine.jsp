<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.sae502.uniplanify.*" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
<%@ page import="fr.sae502.uniplanify.view.Daily" %>
<%@ page import="fr.sae502.uniplanify.view.Weekly" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - UniPlanify</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>

    <header>
        <nav>
            <ul>
                <li><div class="logo"><a href="/"><img src="../img/logo.png" alt="Logo UniPlanify"></a></div></li>
                <li><a href="../my">Mon espace</a></li>
                <li><a href="/">Calendrier</a></li>
                <li><a href="../contact">Contact</a></li>
                <li><a href="../logout">Deconnexion</a></li>
            </ul>
        </nav>
    </header>

    <% Weekly semaine = (Weekly) request.getAttribute("semaine"); 
    %>

    


<div class="semaine">
    <div class="troiscolonnes">
        <div class="left">
            <button type="button"><div>
                <a href="?dayDebut=${dayDebutPreviousWeek}&monthDebut=${monthDebutPreviousWeek}&yearDebut=${yearDebutPreviousWeek}&dayFin=${dayFinPreviousWeek}&monthFin=${monthFinPreviousWeek}&yearFin=${yearFinPreviousWeek}"> < Semaine précedente</a>
            </div></button>
        </div>
        <div class="center">
            <h3><%=semaine.getTitle()%></h3>
        </div>
        <div class="right">
            <button type="button"><div>
                <a href="?dayDebut=${dayDebutNextWeek}&monthDebut=${monthDebutNextWeek}&yearDebut=${yearDebutNextWeek}&dayFin=${dayFinNextWeek}&monthFin=${monthFinNextWeek}&yearFin=${yearFinNextWeek}">Semaine suivante ></a>
            </div></button>
        </div>
    </div>
    
    
    <div class="week-container">
    <% for (Daily jour : semaine.getJours()) { %>
        <div class="day">
            <h3><%= jour.getTitle() %></h3>
            <p><%= jour.getFillPercentage() %>%</p>
            <ul class="rdv-list">
                <% for (Rdv rdvNow : jour.getRdvs()) { %>

                    <%
                    double pourcentage = rdvNow.getFillPercentage();
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
                    
                    <li style="<%= couleur %>">
                            <%= rdvNow.urlToStringTakeRdv("code:heureDuRdv") %>
                    </li>
            <%  } %> 
            </ul>
        </div>
<%  } %>

    
</div>

</body>
</html>
