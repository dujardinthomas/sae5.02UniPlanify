<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.sae502.uniplanify.*" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
<%@ page import="fr.sae502.uniplanify.models.Jour" %>
<%@ page import="fr.sae502.uniplanify.models.Semaine" %>
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
                <li><a href="../perso">Mon espace</a></li>
                <li><a href="/">Calendrier</a></li>
                <li><a href="../contact">Contact</a></li>
                <li><a href="../deconnexion">Deconnexion</a></li>
            </ul>
        </nav>
    </header>

    <h1>Bienvenue sur le calendrier</h1>

    <% Semaine semaine = (Semaine) request.getAttribute("semaine"); 
    %>

    <div class="settingsCalendar">
        <a href="?dayDebut=${dayDebutPreviousWeek}&monthDebut=${monthDebutPreviousWeek}&yearDebut=${yearDebutPreviousWeek}&dayFin=${dayFinPreviousWeek}&monthFin=${monthFinPreviousWeek}&yearFin=${yearFinPreviousWeek}">Semaine précedente</a>
        <a href="/week">Semaine actuelle</a>
        <a href="?dayDebut=${dayDebutNextWeek}&monthDebut=${monthDebutNextWeek}&yearDebut=${yearDebutPreviousWeek}&dayFin=${dayFinNextWeek}&monthFin=${monthFinNextWeek}&yearFin=${yearFinNextWeek}">Semaine suivante</a>
    </div>

<div class="semaine">


    <h2><%=semaine.getTitle()%></h2>
    <table class="week">
        <tr> 
            
            <% for (Jour jour : semaine.getJours()) { %>
            <td>
                <table>
                    <tr>
                        <td><%= jour.getTitle() %></td>
                    </tr>
                    
                <% for (Rdv rdvNow : jour.getRdvs()) { %>
                    <tr>
                        <td>
                            <div class="cellule" style=<%= rdvNow.getStyle() %>>
                                <div class="dayNumber"> 
                                    <%= rdvNow.urlToStringTakeRdv("code:heureDuRdv") %>
                                </div>
                        </div> 
                        </td>
                    </tr>
                <% } %>
                </table>
                </td>
            <% } %>
            
        </tr>
    </table>
        


    
    
</div>
</body>
</html>
