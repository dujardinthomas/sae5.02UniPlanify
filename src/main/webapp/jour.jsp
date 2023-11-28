<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="fr.uniplanify.models.dto.Rdv" %>


<% List<Rdv> listRdvDay = (List<Rdv>) session.getAttribute("listRdvDay"); %>


<table>
    <tr>
        <td><%= session.getAttribute("selectedDate") %></td>
    </tr>

    <%
    for (Rdv rdvNow : listRdvDay) {
    %>
        <tr>
            <td>
                <%= rdvNow.toStringJour() %>
            </td>
        </tr>
    <% } %>


</table>