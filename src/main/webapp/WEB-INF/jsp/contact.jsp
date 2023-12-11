<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Contact</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY"></script>
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
    <div class="container">
        <h1>Contactez-nous</h1>
        <div id="map"></div>
        <div class="contact-info">
            <h2>Informations de contact</h2>
            <p><strong>Adresse :</strong> 123 Rue du Contact, Ville, Pays</p>
            <p><strong>Téléphone :</strong> +1 234 567 890</p>
            <p><strong>Email :</strong> contact@example.com</p>
        </div>
    </div>
    <script>
        initMap();
    </script>
</body>
</html>
