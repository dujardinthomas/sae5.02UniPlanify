<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription - UniPlanify</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
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
                <li><a href="../contact">Contact</a></li>
                <li><a href="../logout">Deconnexion</a></li>
            </ul>
        </nav>
    </header>

    <h1><a href="inscription">Inscription</a></h1>
    
        <form action="inscription" method="post">
            <div class="container">
            <% String msg = request.getParameter("msg"); 
                if(msg != null) { %>
                    <div class="erreur"><%= msg %></div>
                <% }
            %>
                <label for="uname"><b>Nom</b></label>
                <input type="text" placeholder="Enter votre nom" name="nom" required>

                <label for="uname"><b>Prenom</b></label>
                <input type="text" placeholder="Entrer votre prénom" name="prenom" required>

                <label for="uname"><b>Mail (identifiant)</b></label>
                <input type="text" placeholder="Entrer votre mail" name="email" required>

                <label for="psw"><b>Mot de passe</b></label>
                <input type="password" placeholder="Entrer votre mot de passe" name="password" required>
                
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit">Créer mon compte</button>
            </div>

        </form>

        <h1><a href="login">Login</a></h1>
</body>
</html>    