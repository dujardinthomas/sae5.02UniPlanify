<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - UniPlanify</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <h1>Bienvenue sur le calendrier</h1>

    ${calendrierHTML}


    <div class="settingsCalendar">
        <a href="?year=${previousYear}&month=${previousMonth}">Mois précedent</a>
        <a href="/">Aujourd'hui</a>
        <a href="?year=${nextYear}&month=${nextMonth}">Mois suivant</a>
    </div>
    
</body>
</html>