<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - UniPlanify</title>
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

    <form class="form-signin" method="post" action="/login">
      <div class="container">

        <% String msg = request.getParameter("msg"); 
          if(msg != null) { %>
              <div class="erreur"><%= msg %></div>
          <% }
        %>

        <label for="username" class="sr-only">Username</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus>

        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>

        <p><input type='checkbox' name='remember'/> Remember me on this computer.</p>

        <button type="submit">Login</button>
      </div>

    </form>

    <h1><a href="inscription">Inscription</a></h1>
</body>
</html>    























 

</body>
</html>