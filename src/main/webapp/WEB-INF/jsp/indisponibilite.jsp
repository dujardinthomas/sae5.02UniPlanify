<%  request.setAttribute("pageTitle", "Renseignez vos indisponibilités - UniPlanity");
        request.setAttribute("cheminAccueil", "../");
%> 
    
    <form action="Indisponibilite" method="get">

        <h1>Ajout d'une Indisponibilité</h1>

        <table>
            <tr>
                <td>Debut Jour</td>
                <td><input type="date" name="debutjour" id="debutjour"></td>
            </tr>
            <tr>
                <td>Debut heure</td>
                <td><input type="time" name="debutheure" id="debutheure"></td>
            </tr>
            <tr>
                <td>Fin Jour</td>
                <td><input type="date" name="finjour" id="finjour"></td>
            </tr>
            <tr>
                <td>Fin heure</td>
                <td><input type="time" name="finheure" id="finheure"></td>
            </tr>
        </table>

        <input type="submit" value="Enregistrer">
    </form>