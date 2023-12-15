<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.sae502.uniplanify.*" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
<%@ page import="fr.sae502.uniplanify.models.Jour" %>
<%@ page import="fr.sae502.uniplanify.models.Calendrier" %>
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
                <li><a href="/week">Semaine</a></li>
                <li><a href="../contact">Contact</a></li>
                <li><a href="../deconnexion">Deconnexion</a></li>
            </ul>
        </nav>
    </header>

    <h1>Bienvenue sur le calendrier</h1>

    <% Calendrier calendrier = (Calendrier) request.getAttribute("calendrier"); 
    int year = calendrier.getYear();
    int month = calendrier.getMonth();
    %>

<div class="calendrier">


    <h2><%=calendrier.getMonthName()%> <%=year%></h2>
    <table class="calendar">
        <th>Lundi</th>
        <th>Mardi</th>
        <th>Mercredi</th>
        <th>Jeudi</th>
        <th>Vendredi</th>
        <th>Samedi</th>
        <th>Dimanche</th>
        <tr>
        <%-- Pour le decalage par ex le 1er peut etre un vendredi donc on ajoute autant de case jusqu'a vendredi --%>
        <% for (int i = 1; i < calendrier.getStartDayOfWeek(); i++) { %>
                <td> </td>
        <% } %>

        <% for (Jour jour : calendrier.getListJours()) { %>
            <%-- chaque fin de semaine on passe une ligne --%>
            <% if (jour.getPositionSemaine() == 1) { %>
                <tr>
            <% } %>
            <td>
                <% if (jour.getOuvert()){ %>
                     <div class="cellule">
                        <div class="dayNumber"> 
                            <a href="Jour?day=<%=jour.getDay()%>&month=<%=jour.getMonth()%>&year=<%=jour.getYear()%>"><%=jour.getDay()%></a>
                        </div>
                    </div>
                <% } else { %>
                    <div class="celluleClose">
                        <div class="dayNumber"> <%=jour.getDay()%></div>
                    </div>
                <% } %>
            </td>
            <%-- on ferme la ligne dès la fin de la semaine --%>
            <% if (jour.getPositionSemaine() == 7) { %>
                </tr>
            <% } %>
        <% } %>
        </tr>
    </table>
        


    <div class="settingsCalendar">
        <a href="?year=${previousYear}&month=${previousMonth}">Mois précedent</a>
        <a href="/">Aujourd'hui</a>
        <a href="?year=${nextYear}&month=${nextMonth}">Mois suivant</a>
    </div>
    
</div>
</body>
</html>
