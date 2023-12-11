<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
<%@ page import="fr.sae502.uniplanify.models.Utilisateur" %>
<%@ page import="fr.sae502.uniplanify.models.Indisponibilite" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmation d'ajout d'indisponibilité - UniPlanify</title>
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

    <% Indisponibilite indispo = (Indisponibilite)request.getAttribute("indispo"); %>
    <% List<Rdv> rdvHasRemoveList = (List<Rdv>)request.getAttribute("hasDeleteList"); %>
    <% Utilisateur user = (Utilisateur)request.getAttribute("user"); %>


    <% if(indispo != null){ %>
        <h1>Félicitation ${user.getPrenom()} ${user.getNom()}, votre indisponibilité du 
        ${indispo.getDebutJour()} ${indispo.getDebutHeure()} au 
        ${indispo.getFinJour()} ${indispo.getFinHeure()} à été crée !</h1>

        <% if(rdvHasRemoveList != null){ %>
            <h2>Les rendez-vous suivants ont été supprimés car ils se trouvaient dans votre indisponibilité :</h2>
            <ul>
                <% for(Rdv rdv : rdvHasRemoveList){ %>
                    <li>${rdv}</li>
                <% } %>
            </ul>
        <% } %>

    <% }else{ %>
        <h1>Nous rencontrons une erreur lors de l'ajout de votre indisponibilité car vous l'avez déja saisit !</h1>
    <% } %>



    <% if(user.isPro()){ %>

        Acceder à votre <a href="/pro">profil</a> ou retourner à la <a href="/">page d'accueil</a>
        
    <% }else{ %>
        Acceder à votre <a href="/perso">profil</a> ou retourner à la <a href="/">page d'accueil</a>
    <% } %>

    

  
    
</body>
</html>