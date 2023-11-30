<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="fr.uniplanify.models.dto.Rdv" %>
<%@ page import="fr.uniplanify.controllers.ListeRDVJourController" %>


<% 

ListeRDVJourController listeRDVJourController = new ListeRDVJourController();

LocalDate today = (LocalDate) session.getAttribute("selectedDate");
if(today == null){
    today = LocalDate.now();
}

List<Rdv> listRdvDay = listeRDVJourController.getRdvStatus(today);
%>

<table>
    <tr>
        <td><%= session.getAttribute("selectedDateString") %></td>
    </tr>

    <%
    if (listRdvDay.isEmpty()) {
    %>
        <p>La journée est actuellement fermée, aucun rendez-vous disponible.</p>
    <%
    } else {
        try {
            for (Rdv rdvNow : listRdvDay) { %>
                <tr>
                    <td>
                        <%= rdvNow.toStringJour() %>
                    </td>
                </tr>
            <% } %>
        <% 
        } catch (Exception e) { %>
            <p>Une erreur s'est produite lors de la récupération de la journée</p>
        <% }
    } %>

</table>