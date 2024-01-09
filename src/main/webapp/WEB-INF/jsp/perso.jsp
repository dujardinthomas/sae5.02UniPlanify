<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
<%@ page import="fr.sae502.uniplanify.models.UserAccount" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Espace Personnel - UniPlanify</title>
    <link rel="icon" href="../img/logo.png" type="image/png">
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>

    <header>
        <nav>
            <ul>
                <li><div class="logo"><a href="/"><img src="../img/logo.png" alt="Logo UniPlanify"></a></div></li>
                <li><a href="../my">Mon espace</a></li>
                <li><a href="/">Calendrier</a></li>
                <li><a href="/week">Semaine</a></li>
                <li><a href="../contact">Contact</a></li>
                <li><a href="../logout">Deconnexion</a></li>
            </ul>
        </nav>
    </header>

    <h1>Bienvenue Mr ${user.getPrenom()} !</h1>
    <h2>Voici vos rendez-vous à venir :</h2>

       <table border="1">
    <thead>
        <tr>
            <th>Date</th>
            <th>Heure</th>
            <th>Nombre de personne</th>
            <th>Nom</th>
            <th>Prenom</th>
            <th>Etat</th>
            <th>Commentaire</th>
            <th colspan=1>Gestion</th>
        </tr>
    </thead>
    <tbody>
        <% 
        List<Rdv> rdvsList = (List<Rdv>) request.getAttribute("rdvs");
        if(rdvsList == null) rdvsList = new ArrayList<>();
        for (Rdv rdv : rdvsList) { 
        %>
            <tr>
                <td><%= rdv.getLocalDate() %></td>
                <td><%= rdv.getLocalTime() %></td>
                <td><%= rdv.getParticipants().size() %></td>
                <td>
                    <table>
                        <% for(UserAccount user : rdv.getParticipants()) {%>
                        <tr>
                        <td><%= user.getNom() %></td>
                        </tr>
                        <% } %>
                    </table>
                </td>
                <td>
                    <table>
                        <% for(UserAccount user : rdv.getParticipants()) {%>
                        <tr>
                        <td><%= user.getPrenom() %></td>
                        </tr>
                        <% } %>
                    </table>
                </td>
                <td><%= rdv.getState() %></td>
                <td><%= rdv.getComment() %></td>
                <td><a href="rdv/confirmSuppressionRdvParticipant?year=<%= rdv.getYear() %>&month=<%= rdv.getMonth() %>&day=<%= rdv.getDay()%>&hours=<%= rdv.getHours() %>&minutes=<%= rdv.getMinutes() %>">❌</a></td>

            </tr>
        <% } %>
    </tbody>
    </table>

     <h1> Vous souhaitez changer vos informations de compte ? C'est par <a href="/perso/profil">ici</a> !</h1>
    
</body>
</html>