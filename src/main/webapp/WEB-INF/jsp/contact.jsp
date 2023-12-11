<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Contact</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCz4M8SJbfObDejaVIgyakqBW6AKHvYYkg"></script>
    <script>
        function initMap() {
            var myLatLng = {lat: 37.7749, lng: -122.4194}; // Coordonnées de votre emplacement

            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 12,
                center: myLatLng
            });

            var marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                title: 'Notre emplacement'
            });
        }
    </script>
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

    <div class="container">
        <h1>Contactez ${contraintes.getNom()}</h1>

        <p>${contraintes.getDescription()}</p>

        <h2>A propos des rendez-vous</h2>
        Nos rendez-vous ont une durée de ${contraintes.getDureeDefaultMinutes()} minutes. ${contraintes.getNbPersonneMaxDefault()} personne peut participer au même rendez-vous.

        <div id="map"></div>
        <div class="contact-info">
            <h2>Informations de contact</h2>
            <p><strong>Adresse :</strong> ${contraintes.getAdresse()}</p>
            <p><strong>Téléphone :</strong> ${contraintes.getTelephone()}</p>
            <p><strong>Email :</strong> <a href="mailto:${contraintes.getEmail()}">${contraintes.getEmail()}</a></p>
        </div>
    </div>
    <script>
        initMap();
    </script>
</body>
</html>
