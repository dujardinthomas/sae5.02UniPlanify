<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.sae502.uniplanify.models.Utilisateur" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editer mon profil - UniPlanify</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>

    <header>
        <nav>
            <ul>
                <li><div class="logo"><a href="/"><img src="../img/logo.png" alt="Logo UniPlanify"></a></div></li>
                <li><a href="../perso">Mon espace</a></li>
                <li><a href="/">Calendrier</a></li>
                <li><a href="../contact">Contact</a></li>
                <li><a href="../deconnexion">Deconnexion</a></li>
            </ul>
        </nav>
    </header>

    <h1> Edition du profil </h1>

    

    <div class="update-profile">
        <h2>Mettre à jour les informations de compte</h2>
        <form action="profil" method="post" enctype="multipart/form-data">
            <div class="container">
                <label for="uname"><b>Nom</b></label>
                <input type="text" placeholder="Enter votre nom" name="nom" value="${user.getNom()}" required>

                <label for="uname"><b>Prenom</b></label>
                <input type="text" placeholder="Entrer votre prénom" name="prenom" value="${user.getPrenom()}" required>

                <label for="uname"><b>Mail (identifiant)</b></label>
                <input type="text" placeholder="Entrer votre mail" name="email" value="${user.getEmail()}" required>

                <label for="psw"><b>Mot de passe</b></label>
                <input type="password" placeholder="Entrer votre mot de passe" name="password" value="${user.getPassword()}" required>

                <input type="file" name="avatar"/>

                <input type="hidden" name="origine" value="${origine}" >

                <button type="submit">Appliquer les changements</button>
            </div>

        </form>
    </div>


    <!-- <form method="post" action="/fileuploadservlet" enctype="multipart/form-data">
        <input type="file" name="file" />
        <input type="submit" value="Upload" />
    </form> -->


    Voici votre image actuel :
    <img src="${user.getUrlphoto()}" alt="Photo de profil" width="100" height="100">
    ${user}

    
    
    
</body>
</html>