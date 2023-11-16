<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription</title>
</head>

<body>
    <h1><a href="inscription.jsp">Inscription</a></h1>

    <% String mess=request.getParameter("mess"); if (mess!=null) out.println("<h2>"+mess+"</h2>");

        String origine=(String)session.getAttribute("origine");
        if (origine==null) origine="private/welcome1" ;
        %>

        <form action="inscription" method="post">
            <div class="container">
                <label for="uname"><b>Nom</b></label>
                <input type="text" placeholder="Enter votre nom" name="nom" required>

                <label for="uname"><b>Prenom</b></label>
                <input type="text" placeholder="Entrer votre prénom" name="prenom" required>

                <label for="uname"><b>Mail (identifiant)</b></label>
                <input type="text" placeholder="Entrer votre mail" name="login" required>

                <label for="psw"><b>Mot de passe</b></label>
                <input type="password" placeholder="Entrer votre mot de passe" name="pwd" required>

                <input type="hidden" name="origine" value=<%=origine%> >

                <button type="submit">Créer mon compte</button>
            </div>

        </form>

        <h1><a href="login.jsp">Login</a></h1>
</body>

</html>