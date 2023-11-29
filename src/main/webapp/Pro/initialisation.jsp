<%  request.setAttribute("pageTitle", "Renseignez vos indisponibilités - UniPlanity");
        request.setAttribute("cheminAccueil", "../");
%> 
    <form action="Initialisation" method="get">

        <h1>Sélection des contraintes de RDV</h1>

        Durée d'un rdv : <input type="number" name="dureeDefaut" id="dureeDefaut">
        Nombre de personne maximum dans un rdv : <input type="number" name="nbPersonneMax" id="nbPersonneMax">

        <h1>Sélection des jours travaillés</h1>

        <table>
            <tr>
                <th>Jour</th>
                <th>Travaillé?</th>
                <th>Heure de début</th>
                <th>Heure de fin</th>
            </tr>
            <tr>
                <td>Lundi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="lundi"></td>
                <td><input type="time" name="debut_lundi"></td>
                <td><input type="time" name="fin_lundi"></td>

            </tr>
            <tr>
                <td>Mardi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="mardi"></td>
                <td><input type="time" name="debut_mardi"></td>
                <td><input type="time" name="fin_mardi"></td>
            </tr>
            <tr>
                <td>Mercredi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="mercredi"></td>
                <td><input type="time" name="debut_mercredi"></td>
                <td><input type="time" name="fin_mercredi"></td>
            </tr>
            <tr>
                <td>Jeudi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="jeudi"></td>
                <td><input type="time" name="debut_jeudi"></td>
                <td><input type="time" name="fin_jeudi"></td>
            </tr>
            <tr>
                <td>Vendredi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="vendredi"></td>
                <td><input type="time" name="debut_vendredi"></td>
                <td><input type="time" name="fin_vendredi"></td>
            </tr>
            <tr>
                <td>Samedi</td>
                <td><input type="checkbox" name="jour_travaille[]" value="samedi"></td>
                <td><input type="time" name="debut_samedi"></td>
                <td><input type="time" name="fin_samedi"></td>
            </tr>
            <tr>
                <td>Dimanche</td>
                <td><input type="checkbox" name="jour_travaille[]" value="dimanche"></td>
                <td><input type="time" name="debut_dimanche"></td>
                <td><input type="time" name="fin_dimanche"></td>
            </tr>
        </table>

        <input type="submit" value="Enregistrer">
    </form>