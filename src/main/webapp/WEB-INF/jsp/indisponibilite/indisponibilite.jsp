<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="fr">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Renseignez vos indisponibilités - UniPlanity</title>
        <link rel="stylesheet" href="../css/style.css">
    </head>

    <body>

        <header>
            <nav>
                <ul>
                    <li>
                        <div class="logo"><a href="/"><img src="../img/logo.png" alt="Logo UniPlanify"></a></div>
                    </li>
                    <li><a href="../my">Mon espace</a></li>
                    <li><a href="/">Calendrier</a></li>
                    <li><a href="../contact">Contact</a></li>
                    <li><a href="../logout">Deconnexion</a></li>
                </ul>
            </nav>
        </header>

            <form action="indispo" method="post">

                <h1>Ajout d'une Indisponibilité</h1>

                <table>
                    <tr>
                        <td>Jour</td>
                        <td><input type="date" name="jour" id="jour"></td>
                    </tr>
                    <tr>
                        <td>Debut heure</td>
                        <td><input type="time" name="debutheure" id="debutheure"></td>
                    </tr>
                    <tr>
                        <td>Fin heure</td>
                        <td><input type="time" name="finheure" id="finheure"></td>
                    </tr>
                    <tr>
                        <td>Motif</td>
                        <td><input type="text" name="motif" id="motif" value="non renseigné"></td>
                    </tr>
                </table>

                <!-- spring security -->
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <input type="submit" value="Enregistrer">
            </form>


    </body>

    <script>
        const today = new Date();

        // remplis a la date du jour
        const inputDate = document.getElementById('jour');
        const yyyy = today.getFullYear();
        let mm = String(today.getMonth() + 1).padStart(2, '0'); // Ajoute un 0 devant si le mois est sur un seul chiffre
        let dd = String(today.getDate()).padStart(2, '0');
        const formattedDate = yyyy + '-' + mm + '-' + dd;
        inputDate.value = formattedDate;

        // remplis l'heure de début à l'heure actuelle
        const inputTime = document.getElementById('debutheure');
        let hh = String(today.getHours()).padStart(2, '0'); // Ajoute un 0 devant si l'heure est sur un seul chiffre
        let min = String(today.getMinutes()).padStart(2, '0'); 
        const formattedTime = hh + ':' + min;
        inputTime.value = formattedTime;

    </script>

</html>