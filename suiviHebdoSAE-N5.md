# Thomas DUJARDIN [SAE-N5]

## 1ère semaine : Création de la base de données

Les paramètres génériques seront : 
- duree du rdv en minutes
- nbPersonneMax dans un rdv

Voici mon MLD : 

    Client(#idC, nomC, prenomC, mailC, password, pro);
    rdv(#jour, #heure, duree, nbPersonneMax, etat);
    rdvClient([#jour], [#heure] [#idC]);
    Indisponibilite(#debutJour, #debutHeure, #finJour, #finHeure);
    SemaineType(#jourSemaine, heureDebut, heureFin);

J'ai choisi de créer une relation many to many pour les rendez vous client comme ca nous pouvons ajouter autant de client que l'on souhaite (Généricité), (je ferais une verif dans la servlet avec la valeur de nbPersonneMax pour éviter de dépasser cette dernière).

Enfin, j'ai créé quelques données dans chaque table et effectué quelques requetes comme la récuperation des rdv du jour : 

``
    SELECT
    rdv.heure,
    client.nomC,
    client.prenomC,
    rdv.etat
    FROM
    rdv
    INNER JOIN
    rdvClient
    ON
    rdv.jour = rdvClient.jour
    AND
    rdv.heure = rdvClient.heure
    INNER JOIN
    client
    ON
    rdvClient.idC = client.idC
    WHERE
    rdv.jour = DATE( NOW()); 
``



## 2e semaine : Création du context Tomcat et affichage d'un calendrier avec un compteur de click ()

J'ai créer une servlet qui affiche un calendrier sur une page avec l'objet LocalDate en java sous forme de tableau. Il affiche par défaut le mois actuelle.

J'ai ajouté des boutons qui permettent d'afficher le mois suivant, le mois précedent et le mois actuelle sous la forme d'un href en récuperant le paramètre du mois + 1 ou -1.
Pour l'année, si le mois est inférieur à 1 ou supérieur à 12, j'incremente ou je décremente l'année.

Pour chaque jour (case), j'ai crée un compteur cliquable qui s'incrémente lors du clic sur ce lien. Pour cela, j'ai créé une hasmap générale que je place dans une session pour la conserver tant que l'utilisateur est connecté.
Pour la mettre différente d'un mois à l'autre, je l'identifie avec son année et son mois

J'ai commencé la création d'une servlet de sécurisation du context. J'ai placé les informations de connexions à la base de données dans le fichier */WEB-INF/web.xml*
