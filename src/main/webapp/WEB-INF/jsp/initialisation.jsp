<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - UniPlanify</title>
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
    

<%  request.setAttribute("pageTitle", "Renseignez vos indisponibilités - UniPlanity");
        request.setAttribute("cheminAccueil", "../");
%> 
    <form action="init" method="post">


        <h1>Définition du lieu</h1>
        <ul>
            <li>Nom du lieu : <input type="text" name="nom" id="nom" value="${contraintes.getNom()}"></li>
            <li>Description du lieu : <input type="text" name="description" id="description" value="${contraintes.getDescription()}"></li>
            <li>Adresse du lieu : <input type="text" name="adresse" id="adresse" value="${contraintes.getAdresse()}"></li>
            <li>Telephone du lieu : <input type="text" name="telephone" id="telephone" value="${contraintes.getTelephone()}"></li>
            <li>Email du lieu : <input type="text" name="email" id="email" value="${contraintes.getEmail()}"></li>
        </ul>


        <h1>Sélection des contraintes de RDV</h1>
        <ul>
            <li>Durée d'un rdv : <input type="number" name="dureeDefaut" id="dureeDefaut" value="${contraintes.getDureeDefaultMinutes()}"></li>
            <li>Nombre de personne maximum dans un rdv : <input type="number" name="nbPersonneMax" id="nbPersonneMax" value="${contraintes.getNbPersonneMaxDefault()}"></li>
        </ul>

        <h1>Sélection des jours travaillés</h1>

        <table>
            <tr>
                <th>Jour</th>
                <th>Travaillé?</th>
                <th>Heure de début</th>
                <th>Heure de fin</th>
            </tr>
            <tr>
                <td>Lundi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="lundi"></td>
                <td><input type="time" name="debut_lundi"></td>
                <td><input type="time" name="fin_lundi"></td>

            </tr>
            <tr>
                <td>Mardi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="mardi"></td>
                <td><input type="time" name="debut_mardi"></td>
                <td><input type="time" name="fin_mardi"></td>
            </tr>
            <tr>
                <td>Mercredi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="mercredi"></td>
                <td><input type="time" name="debut_mercredi"></td>
                <td><input type="time" name="fin_mercredi"></td>
            </tr>
            <tr>
                <td>Jeudi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="jeudi"></td>
                <td><input type="time" name="debut_jeudi"></td>
                <td><input type="time" name="fin_jeudi"></td>
            </tr>
            <tr>
                <td>Vendredi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="vendredi"></td>
                <td><input type="time" name="debut_vendredi"></td>
                <td><input type="time" name="fin_vendredi"></td>
            </tr>
            <tr>
                <td>Samedi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="samedi"></td>
                <td><input type="time" name="debut_samedi"></td>
                <td><input type="time" name="fin_samedi"></td>
            </tr>
            <tr>
                <td>Dimanche</td>
                <td><input type="checkbox" name="jour_travaille[]" value="dimanche"></td>
                <td><input type="time" name="debut_dimanche"></td>
                <td><input type="time" name="fin_dimanche"></td>
            </tr>
        </table>

        <input type="submit" value="Enregistrer">
    </form>

</body>
</html>
