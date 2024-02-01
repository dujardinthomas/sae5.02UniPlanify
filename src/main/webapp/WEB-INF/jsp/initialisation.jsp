<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.sae502.uniplanify.models.TypicalDayPro" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Initialisation - UniPlanify</title>
    <link rel="icon" href="../img/logo.png" type="image/png">
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
    
    <form action="init" method="post">


        <h1>Définition du lieu</h1>
        <ul>
            <li>Nom du lieu : <input type="text" name="nom" id="nom" value="${contraintes.getNom()}" required></li>
            <li>Description du lieu : <input type="text" name="description" id="description" required value="${contraintes.getDescription()}"></li>
            <li>Adresse du lieu : <input type="text" name="adresse" id="adresse" required value="${contraintes.getAdresse()}"></li>
            <li>Telephone du lieu : <input type="text" name="telephone" id="telephone" required value="${contraintes.getTelephone()}"></li>
            <li>Email du lieu : <input type="text" name="email" id="email" required value="${contraintes.getEmail()}"></li>
        </ul>


        <h1>Sélection des contraintes de RDV</h1>
        <ul>
            <li>Durée d'un rdv : <input type="number" name="dureeDefaut" required id="dureeDefaut" value="${contraintes.getDureeDefaultMinutes()}"></li>
            <li>Nombre de personne maximum dans un rdv : <input type="number" required name="nbPersonneMax" id="nbPersonneMax" value="${contraintes.getNbPersonneMaxDefault()}"></li>
        </ul>

        <h1>Sélection des jours travaillés</h1>
        <% String msg = request.getParameter("error"); 
          if(msg != null) { %>
              <div class="erreur">Il manque les horaires pour <%= msg %> !</div>
          <% }
        %>

        <% List<TypicalDayPro> joursTravailles = (List<TypicalDayPro>) request.getAttribute("journeetypepro");
        if (joursTravailles == null) {
            joursTravailles = new ArrayList<TypicalDayPro>();
        }
    
        boolean lundiChecked = false;
        Object debut_lundi = "";
        Object fin_lundi = "";
        boolean mardiChecked = false;
        Object debut_mardi = "";
        Object fin_mardi = "";
        boolean mercrediChecked = false;
        Object debut_mercredi = "";
        Object fin_mercredi = "";
        boolean jeudiChecked = false;
        Object debut_jeudi = "";
        Object fin_jeudi = "";
        boolean vendrediChecked = false;
        Object debut_vendredi = "";
        Object fin_vendredi = "";
        boolean samediChecked = false;
        Object debut_samedi = "";
        Object fin_samedi = "";
        boolean dimancheChecked = false;
        Object debut_dimanche = "";
        Object fin_dimanche = "";

        for (TypicalDayPro dayPro : joursTravailles) {
            if (dayPro.getDayPro().equals("lundi")) {
                lundiChecked = true;
                debut_lundi = dayPro.getStartTime();
                fin_lundi = dayPro.getEndTime();
            }
            if (dayPro.getDayPro().equals("mardi")) {
                mardiChecked = true;
                debut_mardi = dayPro.getStartTime();
                fin_mardi = dayPro.getEndTime();
            }
            if (dayPro.getDayPro().equals("mercredi")) {
                mercrediChecked = true;
                debut_mercredi = dayPro.getStartTime();
                fin_mercredi = dayPro.getEndTime();
            }
            if (dayPro.getDayPro().equals("jeudi")) {
                jeudiChecked = true;
                debut_jeudi = dayPro.getStartTime();
                fin_jeudi = dayPro.getEndTime();
            }
            if (dayPro.getDayPro().equals("vendredi")) {
                vendrediChecked = true;
                debut_vendredi = dayPro.getStartTime();
                fin_vendredi = dayPro.getEndTime();
            }
            if (dayPro.getDayPro().equals("samedi")) {
                samediChecked = true;
                debut_samedi = dayPro.getStartTime();
                fin_samedi = dayPro.getEndTime();
            }
            if (dayPro.getDayPro().equals("dimanche")) {
                dimancheChecked = true;
                debut_dimanche = dayPro.getStartTime();
                fin_dimanche = dayPro.getEndTime();
            }
        }
        %>

        <table>
            <tr>
                <th>Jour</th>
                <th>Travaillé?</th>
                <th>Heure de début</th>
                <th>Heure de fin</th>
            </tr>
            <tr>
                <td>Lundi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="lundi" <% if (lundiChecked) { %> checked <% } %>></td>
                <td><input type="time" name="debut_lundi" value ="<%= debut_lundi %>"></td>
                <td><input type="time" name="fin_lundi" value ="<%= fin_lundi %>"></td>

            </tr>
            <tr>
                <td>Mardi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="mardi" <% if (mardiChecked) { %> checked <% } %>></td>
                <td><input type="time" name="debut_mardi" value ="<%= debut_mardi %>"></td>
                <td><input type="time" name="fin_mardi" value ="<%= fin_mardi %>"></td>
            </tr>
            <tr>
                <td>Mercredi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="mercredi" <% if (mercrediChecked) { %> checked <% } %>></td>
                <td><input type="time" name="debut_mercredi" value ="<%= debut_mercredi %>"></td>
                <td><input type="time" name="fin_mercredi" value ="<%= fin_mercredi %>"></td>
            </tr>
            <tr>
                <td>Jeudi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="jeudi" <% if (jeudiChecked) { %> checked <% } %>></td>
                <td><input type="time" name="debut_jeudi" value ="<%= debut_jeudi %>"></td>
                <td><input type="time" name="fin_jeudi" value ="<%= fin_jeudi %>"></td>
            </tr>
            <tr>
                <td>Vendredi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="vendredi" <% if (vendrediChecked) { %> checked <% } %>></td>
                <td><input type="time" name="debut_vendredi" value ="<%= debut_vendredi %>"></td>
                <td><input type="time" name="fin_vendredi" value ="<%= fin_vendredi %>"></td>
            </tr>
            <tr>
                <td>Samedi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="samedi" <% if (samediChecked) { %> checked <% } %>></td>
                <td><input type="time" name="debut_samedi" value ="<%= debut_samedi %>"></td>
                <td><input type="time" name="fin_samedi" value ="<%= fin_samedi %>"></td>
            </tr>
            <tr>
                <td>Dimanche</td>
                <td><input type="checkbox" name="jour_travaille[]" value="dimanche" <% if (dimancheChecked) { %> checked <% } %>></td>
                <td><input type="time" name="debut_dimanche" value ="<%= debut_dimanche %>"></td>
                <td><input type="time" name="fin_dimanche" value ="<%= fin_dimanche %>"></td>
            </tr>
        </table>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Enregistrer">
    </form>

</body>
</html>
