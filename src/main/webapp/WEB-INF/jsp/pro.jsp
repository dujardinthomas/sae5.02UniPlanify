<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
<%@ page import="fr.sae502.uniplanify.models.UserAccount" %>
<%@ page import="fr.sae502.uniplanify.models.Unavailability" %>
<%@ page import="fr.sae502.uniplanify.view.Daily" %>
<%@ page import="fr.sae502.uniplanify.view.Weekly" %>

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
                <li><a href="../my">Mon espace</a></li>
                <li><a href="/">Calendrier</a></li>
                <li><a href="../contact">Contact</a></li>
                <li><a href="../logout">Deconnexion</a></li>
            </ul>
        </nav>
    </header>

    <h1>Bienvenue ${user.getPrenom()} ${user.getNom()} sur votre espace administration !</h1>

    <div class="rdv">
    <h2>Voici tous les rendez-vous à venir :</h2>

    

    <% Weekly semaine = (Weekly) request.getAttribute("semaine"); %>

    <div class="semaine">


    <h2><%=semaine.getTitle()%></h2>
    <table class="week">
        <tr> 
            
            <% for (Daily jour : semaine.getJours()) { %>
            <td>
                <table>
                    <tr>
                        <td><%= jour.getTitle() %></td>
                    </tr>
                    <tr>
                        <td><%= jour.getFillPercentage() %>%</td>
                    </tr>
                    
                <% for (Rdv rdvNow : jour.getRdvs()) { %>
                    <% if (rdvNow.getParticipants() != null) { %>
                    <tr>
                        <td>
                        <%
                        double pourcentage = rdvNow.getFillPercentage();
                        String couleur = "";
                        if(pourcentage == 0) {
                            couleur = "background-color: #00FF00"; //vert
                        } else if (pourcentage < 50) {
                            couleur = "background-color: #ADFF2F"; //vert clair
                        } else if (pourcentage < 70) {
                            couleur = "background-color: #FFFF00"; //jaune
                        } else if (pourcentage < 100) {
                            couleur = "background-color: #ff9100"; //orange
                        } else if (pourcentage == 100) {
                            couleur = "background-color: #FF0000"; //rouge
                        } else{
                            couleur = "";
                        }
                        %>
                            <div style="<%= couleur %>">
                                <div> 
                                    <%= rdvNow.getRdvPourLePro() %>
                                </div>
                            </div> 
                        
                        </td>
                    </tr>
                    <% } %>
                <% } %>
                </table>
                </td>
            <% } %>
            
        </tr>
    </table>
    
    <h3> Historique des rendez-vous :</h3>

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
            <th colspan=1>Gestion</th>
        </tr>
    </thead>
    <tbody>
        <% 
        List<Rdv> rdvsList = (List<Rdv>) request.getAttribute("rdvs");
        for (Rdv rdv : rdvsList) { 
        %>
            <tr>
                <td><%= rdv.getLocalDate() %></td>
                <td><%= rdv.getLocalTime() %></td>
                <td><%= rdv.getParticipants().size() %></td>
                <td>
                    <table>
                        <% for(UserAccount user : rdv.getParticipants()) {%>
                        <tr>
                        <td><%= user.getNom() %></td>
                        </tr>
                        <% } %>
                    </table>
                </td>
                <td>
                    <table>
                        <% for(UserAccount user : rdv.getParticipants()) {%>
                        <tr>
                        <td><%= user.getPrenom() %></td>
                        </tr>
                        <% } %>
                    </table>
                </td>
                <td><%= rdv.getState() %></td>
                <td><%= rdv.getComment() %></td>
                <!-- <td><a href="">✏</a></td> -->
                <td><a href="rdv/confirmSuppressionRdv?year=<%= rdv.getYear() %>&month=<%= rdv.getMonth() %>&day=<%= rdv.getDay()%>&hours=<%= rdv.getHours() %>&minutes=<%= rdv.getMinutes() %>">❌</a></td>

            </tr>
        <% } %>
    </tbody>
    </table>
    </div>


    <h3> Vous souhaitez changer vos informations de compte ? C'est par <a href="/pro/profil">ici</a> !</h3>

    <h3> Vous souhaitez ajouter une indisponibilité ? C'est par <a href="/pro/indisponibilite">ici</a> !</h3>


    <div class="indisponibilite">
    <table>
        <th>Jour</th>
        <th>Début</th>
        <th>Fin</th>
        <th>Motif</th>
        <th>Action</th>

        <% List<Unavailability> listIndispo = (List<Unavailability>) request.getAttribute("listIndispo"); %>

        <% for (Unavailability indispo : listIndispo) { %>
            <tr>
                <td><%= indispo.getLocalDate() %></td>
                <td><%= indispo.getStartLocalTime() %></td> 
                <td><%= indispo.getEndLocalTime() %></td>
                <td><%= indispo.getMotif() %></td>
                <td><a href="pro/confirmSuppressionIndispo?jour=<%= indispo.getLocalDate() %>&debutHeure=<%= indispo.getStartLocalTime() %>&finHeure=<%= indispo.getEndLocalTime() %>">❌</a></td>
            </tr>
        <% } %>
    </table>
    </div>

    
    <h3>Redefinir les contraintes</h3>
    <a href="/pro/initialisation">Initialisation</a>
    
</body>
</html>