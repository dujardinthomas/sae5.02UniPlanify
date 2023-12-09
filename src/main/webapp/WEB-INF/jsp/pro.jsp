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
    <title>Espace Professionel - UniPlanify</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <h1>Bienvenue ${user.getPrenom()} ${user.getNom()} sur votre espace administration !</h1>
    <h2>Voici tous les rendez-vous à venir :</h2>

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

    
    
</body>
</html>