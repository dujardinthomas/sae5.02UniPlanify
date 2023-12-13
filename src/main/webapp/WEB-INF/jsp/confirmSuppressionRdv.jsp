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

<h2> Vous souhaitez vraiment supprimer ce rdv 
du ${rdv.getJour()} ${rdv.getHeure()} ? </h2>

<h3> Un e-mail sera envoyé a chaque participant de son annulation. </h3>

<form action="suppressionRdv" method="post">
    

    <input type="hidden" name="year" value="${rdv.getYear()}">
    <input type="hidden" name="month" value="${rdv.getMonth()}">
    <input type="hidden" name="day" value="${rdv.getDay()}">
    <input type="hidden" name="hours" value="${rdv.getHours()}"><input type="hidden" name="year" value="${rdv.getYear()}">
    <input type="hidden" name="minutes" value="${rdv.getMinutes()}">

    <label for="raison">Raison de la suppression qui sera communiqué aux clients :</label>
    <input type="text" name="raison">

    <input type="submit" name="reponse" value="oui">
    <input type="submit" name="reponse" value="non">
</form>

