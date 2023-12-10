<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - UniPlanify</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <header>
        <div class="logo">
            <img src="img/logo.png" alt="Logo UniPlanify">
        </div>
        <nav>
            <ul>
                <li><a href="perso">Mon espace</a></li>
                <li><a href="/">Calendrier</a></li>
                <li><a href="contact">Contact</a></li>
            </ul>
        </nav>
    </header>

<%  request.setAttribute("pageTitle", "Renseignez vos indisponibilités - UniPlanity");
        request.setAttribute("cheminAccueil", "../");
%> 
    
    <form action="indispo" method="post">

        <h1>Ajout d'une Indisponibilité</h1>

        <table>
            <tr>
                <td>Debut Jour</td>
                <td><input type="date" name="debutjour" id="debutjour"></td>
            </tr>
            <tr>
                <td>Debut heure</td>
                <td><input type="time" name="debutheure" id="debutheure"></td>
            </tr>
            <tr>
                <td>Fin Jour</td>
                <td><input type="date" name="finjour" id="finjour"></td>
            </tr>
            <tr>
                <td>Fin heure</td>
                <td><input type="time" name="finheure" id="finheure"></td>
            </tr>
            <tr>
                <td>Motif</td>
                <td><input type="text" name="motif" id="motif" value="non renseigné"></td>
            </tr>
        </table>

        <input type="submit" value="Enregistrer">
    </form>

</body>
</html>
