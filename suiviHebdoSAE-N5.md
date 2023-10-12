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

J'ai créé une servlet qui affiche un calendrier sur une page avec l'objet LocalDate en java sous forme de tableau. Il affiche par défaut le mois actuelle.

J'ai ajouté des boutons qui permettent d'afficher le mois suivant, le mois précedent et le mois actuelle sous la forme d'un href en récuperant le paramètre du mois + 1 ou -1.
Pour l'année, si le mois est inférieur à 1 ou supérieur à 12, j'incremente ou je décremente l'année.

Pour chaque jour (case), j'ai crée un compteur cliquable qui s'incrémente lors du clic sur ce lien. Pour cela, j'ai créé une hasmap générale que je place dans une session pour la conserver tant que l'utilisateur est connecté.
Pour la mettre différente d'un mois à l'autre, je l'identifie avec son année et son mois

J'ai commencé la création d'une servlet de sécurisation du context. J'ai placé les informations de connexions à la base de données dans le fichier */WEB-INF/web.xml*


## 3e semaine : Creation de quelques DAO ET DTO et d'une servlet du jour selectionné

J'ai créé une classe Client.java qui permet de crée un objet client avec ses caracteristiques prisent via la classe ClientDAO.java, ce dernier étant un DAO qui renvoie un client.

Même chose pour un rdv via les classes RdvDAO et RdvClientDAO, ce dernier renvoie une liste de client ajouté au caracteristique de l'objet Rdv. (relation many-to-many)

J'ai modifié le calendrier et quand l'user clique sur un jour cela redirige sur une nouvelle servlet 'jour' qui prend en paramètre le jour, le mois et l'année.

La servlet Jour affiche les créneau de la journée avec l'heure de début, l'heure de fin et la durée d'un rdv. Par exemple, si le pro à des rdv de 15 minutes et commence sa journée à 8h et la finit à 18h, nous avons 8h00, 8h15,...,17h45.