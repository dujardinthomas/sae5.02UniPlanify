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

<h2> Vous souhaitez vraiment supprimer cette indisponibilité 
du ${indispo.getJour()} de ${indispo.getDebutHeure()} à ${indispo.getFinHeure()} ?</h2>

<form action="suppressionIndispo" method="post">
    <input type="submit" name="reponse" value="oui">
    <input type="submit" name="reponse" value="non">

    <input type="hidden" name="jour" value="${indispo.getJour()}">
    <input type="hidden" name="debutHeure" value="${indispo.getDebutHeure()}">
    <input type="hidden" name="finHeure" value="${indispo.getFinHeure()}">
</form>