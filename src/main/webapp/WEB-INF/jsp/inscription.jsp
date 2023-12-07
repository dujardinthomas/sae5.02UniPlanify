<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription - UniPlanify</title>
    <link rel="stylesheet" href="css/style.css">

</head>
<body>

    <h1><a href="inscription">Inscription</a></h1>

    <% String mess = (String) request.getParameter("message"); if (mess!=null) out.println("<h2>"+mess+"</h2>");

        String origine=(String)session.getAttribute("origine");
        if (origine==null) origine="" ;

        //a mettre dans toutes les servlets !
        //on ecrit que l'interieur de la balise body le header, footer c'est dans HeaderFooterFilter !!
        request.setAttribute("pageTitle", "Créer mon compte - UniPlanity");
        request.setAttribute("cheminAccueil", "");
        %>

        <form action="inscription" method="post">
            <div class="container">
                <label for="uname"><b>Nom</b></label>
                <input type="text" placeholder="Enter votre nom" name="nom" required>

                <label for="uname"><b>Prenom</b></label>
                <input type="text" placeholder="Entrer votre prénom" name="prenom" required>

                <label for="uname"><b>Mail (identifiant)</b></label>
                <input type="text" placeholder="Entrer votre mail" name="mail" required>

                <label for="psw"><b>Mot de passe</b></label>
                <input type="password" placeholder="Entrer votre mot de passe" name="password" required>

                <input type="hidden" name="origine" value=<%=origine%> >

                <button type="submit">Créer mon compte</button>
            </div>

        </form>

        <h1><a href="login">Login</a></h1>
</body>
</html>    