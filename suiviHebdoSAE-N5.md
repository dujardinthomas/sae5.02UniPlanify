# Thomas DUJARDIN [SAE-N5]

## 1ère semaine : Création de la base de données

Les paramètres génériques seront : 
- duree du rdv en minutes
- nbPersonneMax dans un rdv

Voici mon MLD : 

    Client(#idC, nomC, prenomC, mailC, password, pro);
    rdv(#jour, #heure, etat);
    rdvClient([#jour], [#heure] [#idC]);
    Indisponibilite(#debutJour, #debutHeure, #finJour, #finHeure);
    semainetypepro(#jourSemaine, heureDebut, heureFin);
    constraints(dureeDefaultMinutes, nbPersonneMaxDefault);

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
Pour la mettre différente d'un mois à l'autre, je l'identifie avec son année et son mois.
J'ai placé un simple lien dans chaque case qui renvoie sur la même page : 

```java
calendarHTML.append("<a href=\"?year=" + year + "&month=" + month + "&day=").append(dayStr).append("\">" + counter +"</a>");
```

(l'incrementation du compteur se fait au début du code de la page) : 

```java
String day = req.getParameter("day");
        if (day != null) {
            Integer counter = counters.get(day);
            if (counter == null) {
                counter = 1;
            } else {
                counter++;
            }
            counters.put(day, counter);
        }
```

J'ai commencé la création d'une servlet de sécurisation du context. J'ai placé les informations de connexions à la base de données dans le fichier */WEB-INF/web.xml*


## 3e semaine : Récréation du projet en Maven, début du respect des principes d’un MVC Web, et avec des DAO ET DTO, et d'une servlet du jour selectionné

J'ai recrée un nouveau projet maven web et y déplacé mes servlets. J'ai configuré mon fichier de configuration pom.xml en y ajoutant les extensions nécessaires (jakarta, postgresql, cargo avec le paramètre tomcat). J'ai placé mon fichier contenant les informations de connexion à la base de données dans un dossier de la racine du projet et crée une classe DS qui crée une connection en lisant le fichier. J'ai ajouté un plugin qui permet de copier ce fichier dans le dossier de compilation target.

J'ai créé dans le package models des DTO et DAO, une classe Client.java qui permet de crée un objet client avec ses caracteristiques prisent via la classe ClientDAO.java, ce dernier étant un DAO qui respecte le modèle CRUD qui renvoie un client.

Même chose pour un rdv via les classes RdvDAO et RdvClientDAO, ce dernier renvoie une liste de client ajouté aux caracteristiques de l'objet Rdv. (relation many-to-many)

Egalement pour une semaineTypePro, son DTO (JournéePro et SemaineTypePro) et son DAO.

J'ai modifié le calendrier et quand l'user clique sur un jour cela redirige sur une nouvelle servlet 'jour' qui prend en paramètre le jour, le mois et l'année.

La servlet Jour affiche tout les créneaux de la journée, qu'il soit disponible (affichage de la case en vert) ou qu'il soit déja reservé (affichage de la case en rouge). Par exemple, si le pro à des rdv de 15 minutes et commence sa journée à 8h et la finit à 18h, nous avons 8h00, 8h15,...,17h45. 
Pour vérifier si le créneau est disponible, je regarde si ma méthode dans mon RDVDAO retourne un objet rendez-vous correspondant à l'heure de ce créneau. Pour afficher tout les créneau, je fais une boucle tant que le créneau iterable est avant la fin de journée (recupere objet SemaineTypeProDAO qui contient une liste de JournéePro qui contient le jour, l'heure de début et l'heure de fin en LocalDate).

J'ai hiérarchisé le code pour qu'il respecte les principes d'un modèle MVC WEB.

## 4e semaine : Authentification, passage à JPA

L'objectif est de rendre le calendrier accessible au public tout en exigeant une authentification pour la réservation et la consultation de son espace. Pour cela, j'ai mis en place 2 filtres ; un qui écoute chaque requête débutant par "Perso" et le second par "Pro" : *@WebFilter(urlPatterns = { "/Perso/*" }).

Ces filtres regardent dans la session si un objet client existe. 
- Pour un client, si il existe, le filtre permet l'accès à la page demandé 
- Pour un admin, le filtre regarde si l'attribut pro est à true. 

Si le client n'existe pas ou qu'il n'est pas pro, le filtre redirige vers la page de connexion, puis vers la servlet de vérification de connexion. Cette servlet va créer l'objet client, depuis la bdd et rediriger vers la page souhaité (L'url de la page intial et ses paramètres sont passés au formulaire de connexion en champ caché permettant la redirection souhaité).
