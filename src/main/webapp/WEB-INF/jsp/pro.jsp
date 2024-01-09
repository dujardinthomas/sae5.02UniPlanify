<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.sae502.uniplanify.models.Rdv" %>
<%@ page import="fr.sae502.uniplanify.models.UserAccount" %>
<%@ page import="fr.sae502.uniplanify.models.Unavailability" %>
<%@ page import="fr.sae502.uniplanify.view.Daily" %>
<%@ page import="fr.sae502.uniplanify.view.Monthly" %>
<%@ page import="fr.sae502.uniplanify.view.Weekly" %>


<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Espace Professionel - UniPlanify</title>
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

    <h1>Bienvenue ${user.getPrenom()} ${user.getNom()} sur votre espace administration !</h1>

    <div class="rdv">
    <h2>Voici tous les rendez-vous à venir :</h2>

    
    <% Monthly calendrier = (Monthly) request.getAttribute("calendrier"); 
    int year = calendrier.getYear();
    int month = calendrier.getMonth();
    %>

<div class="calendrierPro">


    <h2><%=calendrier.getMonthName()%> <%=year%></h2>
    <table class="calendarPro">
        <th>Lundi</th>
        <th>Mardi</th>
        <th>Mercredi</th>
        <th>Jeudi</th>
        <th>Vendredi</th>
        <th>Samedi</th>
        <th>Dimanche</th>
        <tr>
        <%-- Pour le decalage par ex le 1er peut etre un vendredi donc on ajoute autant de case jusqu'a vendredi --%>
        <% for (int i = 1; i < calendrier.getStartDayOfWeek(); i++) { %>
                <td> </td>
        <% } %>

        <% for (Daily jour : calendrier.getListJours()) { %>
            <%-- chaque fin de semaine on passe une ligne --%>
            <% if (jour.getPositionSemaine() == 1) { %>
                <tr>
            <% } %>
            <td>
                <% if (jour.getOuvert()){ %>

                    <%
                    double pourcentageJour = jour.getFillPercentage();
                    String couleurJour = "";
                    if(pourcentageJour == 0) {
                        couleurJour = "background-color: #00FF00"; //vert
                    } else if (pourcentageJour < 50) {
                        couleurJour = "background-color: #ADFF2F"; //vert clair
                    } else if (pourcentageJour < 70) {
                        couleurJour = "background-color: #FFFF00"; //jaune
                    } else if (pourcentageJour < 100) {
                        couleurJour = "background-color: #ff9100"; //orange
                    } else if (pourcentageJour == 100) {
                        couleurJour = "background-color: #FF0000"; //rouge
                    } else{
                        couleurJour = ""; //rien
                    }
                    %>

                     <div class="cellulePro" style="<%= couleurJour %>">
                        <div class="day"> <%=jour.getDay()%></div>
                        <div class="rdv">
                            <% for (Rdv rdv : jour.getRdvs()) { %>
                            <% if (rdv.getParticipants() != null) { %>

                                <%
                    double pourcentage = rdv.getFillPercentage();
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
                        couleur = ""; //rien
                    }
                    %>
                                <div class="rdvItem" style="<%= couleur %>">
                                    <div class="rdvTime"><%= rdv.getRdvPourLePro() %></div>
                                    </div>
                                </div>
                            <% } 
                            } %>
                        </div>
                    </div>
                <% } else { %>
                    <div class="celluleClose">
                        <div class="dayNumber"> <%=jour.getDay()%></div>
                    </div>
                <% } %>
            </td>
            <%-- on ferme la ligne dès la fin de la semaine --%>
            <% if (jour.getPositionSemaine() == 7) { %>
                </tr>
            <% } %>
        <% } %>
        </tr>
    </table>


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