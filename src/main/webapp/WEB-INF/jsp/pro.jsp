<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
<%@ page import="fr.sae502.uniplanify.models.Utilisateur" %>
<%@ page import="fr.sae502.uniplanify.models.Indisponibilite" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Espace Professionel - UniPlanify</title>
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

    <h1>Bienvenue ${user.getPrenom()} ${user.getNom()} sur votre espace administration !</h1>

    <div class="rdv">
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
            <th colspan=2>Gestion</th>
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
                <td><a href="">✏</a></td>
                <td><a href="">❌</a></td>
            </tr>
        <% } %>
    </tbody>
    </table>
    </div>


    <h2> Vous souhaitez changer vos informations de compte ? C'est par <a href="/pro/profil">ici</a> !</h2>

    <h2> Vous souhaitez ajouter une indisponibilité ? C'est par <a href="/pro/indisponibilite">ici</a> !</h2>


    <div class="indisponibilite">
    <table>
        <th>Début</th>
        <th>Fin</th>

        <% List<Indisponibilite> listIndispo = (List<Indisponibilite>) request.getAttribute("listIndispo"); %>

        <% for (Indisponibilite indispo : listIndispo) { %>
            <tr>
                <td>
                    <%= indispo.getDebutJour() %>
                    <%= indispo.getDebutHeure() %>
                </td>
                <td>
                    <%= indispo.getFinJour() %>
                    <%= indispo.getFinHeure() %>
                </td>
            </tr>
        <% } %>
    </table>
    </div>

    
    <h2>Redefinir les contraintes</h2>
    <a href="/pro/initialisation">Initialisation</a>
    
</body>
</html>