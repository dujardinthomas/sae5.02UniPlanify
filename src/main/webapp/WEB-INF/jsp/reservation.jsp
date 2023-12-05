<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
<%@ page import="fr.sae502.uniplanify.models.Utilisateur" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmation de reservation - UniPlanify</title>
    <link rel="stylesheet" href="css/style.css">

</head>
<body>

    <% String statut = (String)request.getAttribute("status"); %>
    <% Utilisateur user = (Utilisateur)request.getAttribute("user"); %>


      <%if(statut.equals("created")){%>
        <h1>Félicitation ${user.getPrenom()} ${user.getNom()}, votre rendez-vous du ${rdv.getJour()} à ${rdv.getHeure()} à été crée !</h1>

    <%}else if(statut.equals("add")){%>
        <h1> ${user.getPrenom()} ${user.getNom()} à été 
        ajouté au rendez-vous du ${rdv.getJour()} à ${rdv.getHeure()}</h1>
    
    <%}else if(statut.equals("plein")){%>
        <h1>Le rendez-vous du ${rdv.getJour()} à ${rdv.getHeure()} est plein !</h1>


    <%}else{
        out.println("erreur de recuperation de statut");
    } %>

    

  
    
</body>
</html>