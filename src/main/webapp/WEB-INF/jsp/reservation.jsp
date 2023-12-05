<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmation de reservation - UniPlanify</title>
    <link rel="stylesheet" href="css/style.css">

</head>
<body>

    <h1>Félicitation ${user.getNom}, votre rendez-vous du ${rdv.getJour} à ${rdv.getHeure} est confirmé !</h1>

    <%if(statut){%>
        <h1>client ${user.getId()} ${statut ? " à été " : "n'a pas été "}
        ajouté au rendez-vous du ${dateDuRdv} à ${heureDuRdv}</h1>


        out.println(
                    "<h1>rendez-vous du " + dateDuRdv + " à " + heureDuRdv
                            + (statut ? " à été crée " : "n'a pas été crée ")
                            + " avec le client " + client.getIdC() + "</h1>");

    <% if()

        out.println("<h1>Vous avez déja résérvé ce rendez-vous du " + dateDuRdv + " à " + heureDuRdv + " !</h1>");
    
</body>
</html>