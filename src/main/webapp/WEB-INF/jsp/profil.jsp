<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="fr.sae502.uniplanify.models.UserAccount" %>
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
                <li><a href="../my">Mon espace</a></li>
                <li><a href="/">Calendrier</a></li>
                <li><a href="/week">Semaine</a></li>
                <li><a href="../contact">Contact</a></li>
                <li><a href="../logout">Deconnexion</a></li>
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

                <label for="psw"><b>Ancien mot de passe</b></label>
                <input type="password" placeholder="Entrer votre mot de passe" name="oldPassword" required>

                <label for="psw"><b>Nouveau mot de passe</b></label>
                <input type="password" placeholder="Entrer votre nouveau mot de passe" name="newPassword" required>

                <label for="avatar"><b>Changer votre photo de profil</b></label>
                <img src="${user.getUrlphoto()}" alt="Photo de profil" width="100" height="100">

                <input type="file" name="avatar"/>

                <input type="hidden" name="origine" value="${origine}" >

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <button type="submit">Sauvegarder votre profil</button>
            </div>

        </form>
    </div>

</body>
</html>