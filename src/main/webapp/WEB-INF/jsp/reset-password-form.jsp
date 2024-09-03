<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reinitialiser mon mot de passe - UniPlanify</title>
    <link rel="icon" href="../img/logo.png" type="image/png">
    <link rel="stylesheet" href="../css/style.css">

</head>
<body>

<%@ include file="/WEB-INF/jsp/template/menu.jsp" %>

 <h1><a href="login">Login</a></h1>

    <form class="form-signin" method="post" action="reset-password-form">
      <div class="container">

        <% String msg = request.getParameter("msg"); 
          if(msg != null) { %>
              <div class="erreur"><%= msg %></div>
          <% }
        %>

        <label for="email" class="sr-only">Username</label>
        <input type="text" id="email" name="email" class="form-control" placeholder="Adresse mail" required autofocus>


        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <button type="submit">Demander un nouveau mot de passe</button>
      </div>

    </form>

    <h1><a href="inscription">Inscription</a></h1>
</body>
<%@ include file="/WEB-INF/jsp/template/footer.jsp" %>
</html>    
