<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Contact</title>
    <link rel="icon" href="../img/logo.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <style>
        #map {
            height: 400px; /* Hauteur de la carte */
            width: 100%; /* Largeur de la carte */
        }
    </style>
</head>
<body>

    <header>
        <nav>
            <ul>
                <li><div class="logo"><a href="/"><img src="../img/logo.png" alt="Logo UniPlanify"></a></div></li>
                <li><a href="my">Mon espace</a></li>
                <li><a href="/">Calendrier</a></li>
                <li><a href="/week">Semaine</a></li>
                <li><a href="../contact">Contact</a></li>
                <li><a href="../logout">Deconnexion</a></li>
            </ul>
        </nav>
    </header>

    
        <h1>Contactez ${contrainte.getNom()}</h1>

        <p>${contrainte.getDescription()}</p>

        <h2>A propos des rendez-vous</h2>
        Nos rendez-vous ont une durée de ${contrainte.getDureeDefaultMinutes()} minutes. ${contrainte.getNbPersonneMaxDefault()} personne peut participer au même rendez-vous.

        <div class="colonne">
            <div class="colonne-gauche">
                <h2>Informations de contact</h2>
                <p><strong>Adresse :</strong> ${contrainte.getAdresse()}</p>
                <p><strong>Téléphone :</strong> ${contrainte.getTelephone()}</p>
                <p><strong>Email :</strong> <a href="mailto:${contrainte.getEmail()}">${contrainte.getEmail()}</a></p>
            </div>

            <div class="colonne-droite">
                <!-- <div id="map"></div> -->
                <iframe src="https://www.google.com/maps/embed/v1/place?key=AIzaSyCrmqimuE6f1RPknysSnXLIDezs4dCxI5Y&q=${contrainte.getAdresse()}"
                    width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
            </div>
        </div>

    <script>
        function initMap() {
            // Créer une instance de géocodeur
            var geocoder = new google.maps.Geocoder();

            // Adresse pour laquelle vous souhaitez obtenir les coordonnées
            var address = "${contrainte.getAdresse()}";

            // Appeler l'API de géocodage pour obtenir les coordonnées
            geocoder.geocode({ 'address': address }, function (results, status) {
                if (status == 'OK') {
                    var map = new google.maps.Map(document.getElementById('map'), {
                        center: results[0].geometry.location,
                        zoom: 15
                    });

                    // Marqueur pour l'emplacement basé sur l'adresse
                    var marker = new google.maps.Marker({
                        map: map,
                        position: results[0].geometry.location,
                        title: 'Votre Emplacement'
                    });
                } else {
                    console.log('La géolocalisation de l\'adresse a échoué : ' + status);
                }
            });
        }
    </script>
    <!-- Inclure le script de l'API Google Maps -->
    <script async
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAGa7Q5bHfSJTcG7kXng7qBxkcnc8JAC1k&callback=initMap">
</script>
</body>
</html>
