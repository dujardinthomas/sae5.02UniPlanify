<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
<%@ page import="fr.sae502.uniplanify.models.UserAccount" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmation de reservation - UniPlanify</title>
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

    <% String statut = (String)request.getAttribute("status"); %>
    <% UserAccount user = (UserAccount)request.getAttribute("user"); %>


    <% if(statut.equals("created")){ %>
        <h1>Félicitation ${user.getPrenom()} ${user.getNom()}, votre rendez-vous du ${rdv.getLocalDate()} à ${rdv.getLocalTime()} à été crée !</h1>

    <% }else if(statut.equals("add")){ %>
        <h1> Félicitation ${user.getPrenom()} ${user.getNom()} à été 
        ajouté au rendez-vous du ${rdv.getLocalDate()} à ${rdv.getLocalTime()}</h1>
    
    <% }else if(statut.equals("plein")){ %>
        <h1>Malheureusement, le rendez-vous du ${rdv.getLocalDate()} à ${rdv.getLocalTime()} est plein !</h1>
        <h2>Impossible de réserver le rdv !</h2>

    <% }else if(statut.equals("doublon")){ %>
        <h1> Malheureusement ${user.getPrenom()} ${user.getNom()} à déja été 
        ajouté au rendez-vous du ${rdv.getLocalDate()} à ${rdv.getLocalTime()}</h1>
        <h2>Impossible de re-réserver le rdv !</h2>

    <% }else if(statut.equals("old")){ %>
        <h1> Malheureusement, le créneau du rendez-vous du ${rdv.getLocalDate()} à ${rdv.getLocalTime()} est déja passé !</h1>
        <h2>Impossible de réserver ce rdv, nous ne pouvons pas encore revenir dans le passé...</h2>

    <% }else if(statut.equals("chevauchement")){ %>
        <h1> Malheureusement, le créneau du rendez-vous du ${rdv.getLocalDate()} à ${rdv.getLocalTime()} chevauche un autre rdv !</h1>
        <h2>Impossible de réserver ce rdv, vous n'êtes pas prioritaire...</h2>

    <% }else if(statut.equals("indispo")){ %>
        <h1> Malheureusement, le créneau du rendez-vous du ${rdv.getLocalDate()} à ${rdv.getLocalTime()} n'est pas disponible !</h1>
        <h2>Impossible de réserver ce rdv, le professionel n'est pas disponible.</h2>

    <% }else{ %>
        <h1>Nous rencontrons une erreur lors de la réservation de votre rendez-vous.</h1>
        <h2>Nous vous invitons à ressayer via la page d'accueil.</h2>
    <% } %>


        Acceder à votre <a href="/my">profil</a> ou retourner à la <a href="/">page d'accueil</a>
  
    
</body>
</html>