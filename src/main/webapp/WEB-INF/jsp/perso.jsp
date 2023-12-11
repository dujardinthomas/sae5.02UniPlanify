<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
<%@ page import="fr.sae502.uniplanify.models.Utilisateur" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Espace Personnel - UniPlanify</title>
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
        </tr>
    </thead>
    <tbody>
        <% 
        List<Rdv> rdvsList = (List<Rdv>) request.getAttribute("rdvs");
        for (Rdv rdv : rdvsList) { 
        %>
            <tr>
                <td><%= rdv.getJour() %></td>
                <td><%= rdv.getHeure() %></td>
                <td><%= rdv.getParticipants().size() %></td>
                <td>
                    <table>
                        <% for(Utilisateur user : rdv.getParticipants()) {%>
                        <tr>
                        <td><%= user.getNom() %></td>
                        </tr>
                        <% } %>
                    </table>
                </td>
                <td>
                    <table>
                        <% for(Utilisateur user : rdv.getParticipants()) {%>
                        <tr>
                        <td><%= user.getPrenom() %></td>
                        </tr>
                        <% } %>
                    </table>
                </td>
                <td><%= rdv.getEtat() %></td>
                <td><%= rdv.getCommentaire() %></td>
            </tr>
        <% } %>
    </tbody>
    </table>

     <h1> Vous souhaitez changer vos informations de compte ? C'est par <a href="/perso/profil">ici</a> !</h1>
    
</body>
</html>