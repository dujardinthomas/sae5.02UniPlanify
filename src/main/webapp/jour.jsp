<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="fr.uniplanify.models.dto.Rdv" %>


<% List<Rdv> listRdvDay = (List<Rdv>) session.getAttribute("listRdvDay");; 

if (listRdvDay == null){
    listRdvDay = new ArrayList<Rdv>();
}
%>

<table>
    <tr>
        <td><%= session.getAttribute("selectedDate") %></td>
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