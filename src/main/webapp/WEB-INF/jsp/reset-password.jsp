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

  <header>
        <nav>
            <ul>
                <li><div class="logo"><a href="/"><img src="../img/logo.png" alt="Logo UniPlanify"></a></div></li>
                <li><a href="my">Mon espace</a></li>
                <li><a href="/">Calendrier</a></li>
                <li><a href="contact">Contact</a></li>
                <li><a href="logout">Deconnexion</a></li>
            </ul>
        </nav>
    </header>



 <h1><a href="login">Login</a></h1>

    <form class="form-signin" method="post" action="reset-password">
      <div class="container">

        <% String msg = request.getParameter("msg"); 
          if(msg != null) { %>
              <div class="erreur"><%= msg %></div>
          <% }
        %>

        <label for="password" class="sr-only">New Password</label>
        <input type="text" id="password" name="password" class="form-control" placeholder="new password" required autofocus>

        <label for="password1" class="sr-only">New Password</label>
        <input type="text" id="password1" name="password1" class="form-control" placeholder="new password" required autofocus>

        <input type="hidden" name="token" value="${token}"/>


        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <button type="submit">Reinitialiser mon mot de passe</button>
      </div>

    </form>

    <h1><a href="inscription">Inscription</a></h1>
</body>
</html>    























 

</body>
</html>