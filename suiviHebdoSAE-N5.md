# Thomas DUJARDIN [SAE-N5]

## 1ère semaine : Création de la base de données

Les paramètres génériques seront : 
- duree du rdv en minutes
- nbPersonneMax dans un rdv

Voici mon MLD : 

    typical_day_pro(#day, start_time, end_time);
    unavailability(#day, #start_time, #end_time, motif);
    constraint_pro(#duree_default_minutes, nb_personne_max_default, adresse, description, email, nom, telephone);
    user_account(#id, nom, prenom, email, password, urlphoto, authority, enabled);
    rdv(#day, #time, state, comment, fill_percentage);
    rdv_participant(#rdv_day, #rdv_time, #participant_id);

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



## 2e semaine : Création du context Tomcat et affichage d'un calendrier mensuel avec un compteur de click ()

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

## 4e semaine : Authentification, Espace Personnel et passage à JPA,

L'objectif premier à été de rendre le calendrier accessible au public tout en exigeant une authentification pour la réservation et la consultation de son espace perso (affiche de ses rdv, modification du profil, ...). Pour cela, j'ai mis en place 2 filtres ; un qui écoute chaque requête débutant par "Perso" et le second par "Pro" : *@WebFilter(urlPatterns = { "/Perso/*" }) (tout ce qui touche aux données personnels est rangé derrière Perso/ ou Pro/).

Ces filtres regardent dans la session si un objet client existe. 
- Pour un client lambda, si il existe, le filtre permet l'accès à la page demandé 
- Pour un admin, le filtre regarde si l'attribut pro du client est à true. 

Si le client n'existe pas ou qu'il n'est pas pro, le filtre redirige vers la page de connexion, puis vers la servlet de vérification de connexion. Cette servlet va créer l'objet client, depuis la bdd, le mettre dans un objet HTTPSession et rediriger vers la page souhaité (L'url de la page intial et ses paramètres sont passés au formulaire de connexion en champ caché permettant la redirection souhaité). Si l'utilisateur n'a pas de compte, il peut s'en créer un automatiquement en remplissant le formulaire accessible sur la page login.

Ensuite, j'ai crée une servlet permettant d'afficher pour le client tous ses rendez-vous et pour le pro tout les rendez-vous de tout le monde. L'utilisateur peut également sur cette même page, modifier son profil, et administrer ses rendez-vous.

Enfin, j'ai passé tout le projet en JPA. J'ai supprimé tous mes DAO manuels et les ai remplacés par l'utilisation des EntityManagers. J'ai principalement utilisé les méthodes *find* et *createNamedQuery* pour récupérer un objet et *persit* pour insérer un objet dans la base de données. 

Voici les étapes :
1. J'ai ajouté la dépendance JPA dans le pom.xml
2. J'ai crée le persistence.xml où j'y ait défini 2 persistence-unit, 1 pour l'initialisation (il recrée le schéma de la base de données avec 1 seul client : l'administrateur) et l'autre qui permet de ne pas le modifier.
3. J'ai supprimé mes DAO et redefinis mes POJO de manière à ce qu'il fonctionne avec un entityManager.
4. J'ai enfin remplacé tous les appels aux DAO par des entityManager.


## 5e semaine : Spring MVC, Indisponibilite

J'ai recrée le projet en me basant sur le framework spring boot mvc avec ses dépendances nécessaires. J'ai effectué les configurations nécessaires (moteur jsp, web, base de donnée h2).
J'ai crée les controlers (qui étaient les servlets avant) et qui renvoient sur les vues correspondantes en jsp.
Chaque controleur qui a besoin de la base de données, est un Composant spring (pour permettre l'accès à l'interface CRUDRepository)

J'ai mis en place la base de données h2 en mode mémoire. Plus besoin de se soucier des connexions ! Les tables sont crées via JPA et remplis via le fichier import.sql à chaque démarrage du projet.

J'ai autorisé le professionel à prendre des indisponibilités. Lorsqu'il sélectionne le jour, l'heure de début et l'heure de fin, c'est enregistré dans la base de donnée. Tout les rendez-vous déja réservés sont automatiquement supprimés (voir pour une alerte envoie de mail...) et l'application ne propose plus de créneaux sur la plage de l'indisponibilité. J'ai également fait attention que un rdv est disponible si il peut se finir sans chevaucher une indisponibilité.

## 6e semaine : Mail, Photo de profil, Vue hebdomadaire

J'ai ajouté une fonctionnalité qui lorsque un rdv est supprimé, ou une indisponibilité enregistré, envoie un mail à tous les participants de ce rdv pour les informer de son annulation. Il est définitivement supprimé de l'application. J'ai ajouté la dépendance spring mail, j'ai configuré le serveur smtp avec un compte *uniplanify@gmail.com* qui est l'adresse expéditeur et crée une fonction pour faciliter son usage multiple.

J'ai également ajouté pour les utilisateurs la possibilité d'uploader sa photo de profil sur le serveur. Lors de leurs inscription, ils se voit attribuer une photo par défaut. Pour cela, j'ai rajouter un champ d'insertion de type file dans le formulaire d'édition du profil. Ensuite je recupère l'image en objet MultipartFile et la renomme avec l'identifiant de l'utilisateur (pour éviter les potentiels doublons de nom). Je l'enregistre via la méthode transferTo dans le dossier *resource/static/img/profils/*. Enfin, je met à jour l'utilisateur avec le chemin relatif de sa photo dans la base de données pour qu'elle puisse s'afficher sur les pages.

J'ai également ajouté en bonus une vue hebdomadaire (qui par défault commence au jour actuel et se termine à j+6). J'ai tout simplement crée un objet semaine où se trouve une liste de 7 jours. J'ai ajouté des boutons permettant d'acceder à la semaine actuelle, précedente ou suivante.

J'ai mis en place un code couleur (sur les vues mensuel, hebdomadaire et jour avec des valeurs arbitraires en html) pour voir le taux d'occupation d'un rdv, et le taux d'occupation sur la journée (visible sur le calendrier mensuel). Plus c'est vert, plus il y a de la place, et plus c'est rouge, moins il y a de place. J'ai pour cela ajouté une fonction de calcul du taux d'occupation dans l'objet rdv et ajouté dans l'objet jour un attribut contenant la moyenne des taux de remplissages des rdv de la journée.