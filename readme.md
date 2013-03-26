=====================================================
Hands on Duchess fr - Cache et BigData

Code from the backbone cellar application developped by Christone Coenraets - https://github.com/ccoenraets/backbone-jax-cellar

====================================================

#Prérequis :
    Maven
    Git
    git clone https://github.com/MathildeLemee/Hands_On_Ehcache.git
    mvn clean install
    mvn jetty:run
    Télécharger BigMemoryMax  + obtenir une license ici : http://www.terracotta.org/downloads/bigmemorymax?set=1
    
#Partie 1

##Exercice 1 :  Cache Aside
Implémenter un cache aside dans le service Exercise1 pour la méthode findBy.
Un cache aside a l'algo suivant :
Si j'ai la valeur dans mon cache alors je la retourne
Sinon je vais la chercher en base de données et je la place dans mon cache

Une fois que vous etes confiant, lancer le serveur jetty avec mvn jetty:run
Rendez-vous sur la page exercise1.html  .
Vous y verrez 2 lignes pour comparer la latence entre un appel à la base de données et l'équivalent en cache.


##Exercice 2 :  Read-Trough
Cache sytem-of-record : l'application ne voit plus que le cache, c'est le garant des données.
Implémenter la lecture via la méthode findBy de Exercise2. Meme lorsque la donnée ne sera pas déja présente dans le cache,
c'est le cache qui saura comment aller la chercher.
Indice : SelfPopulatingCache pourra surement vous aider :)


Rendez vous sur la page exercise2.html pour voir le résultat

##Exercice 3 : Write-Through
Cache as a sytem-of-record : l'application ne voit plus que le cache, c'est le garant des données.
Implémenter l'écriture via la méthode create de Exercise3. La donnée sera écrite dans le cache et c'est le cache qui saura reporter la donnée dans la base de données secondaires, ici H2
L'écriture devra etre synchronisée.
 
Indice : Le cacheWriter pourra surement vous aider :)
 
Rendez vous sur la page exercise3.html pour voir le résultat

##Exercice 4 : Write-Behind
Cache as a sytem-of-record : l'application ne voit plus que le cache, c'est le garant des données.
La donnée sera écrite dans le cache et c'est le cache qui saura reporter la donnée dans la base de données secondaires, ici H2
L'écriture devra etre asynchone.

Tips : C'est très proche du Write-Through
Rendez vous sur la page exercise4.html pour voir le résultat


##Exercice 5 : Bonus : RefreshAhead
Créer un cache avec un time-to-refresh égal à une seconde dans Exercice5.

RefreshAhead permet d'avoir un cache qui se rafraichit en arrière plan après une lecture sur une clé.
Il vous faudra 2 choses :
D'abord la configuration avec la définition du time-to-refresh, durée à partir de laquelle un accès en lecture va déclencher une mise à jour asynchrone.
via RefreshAheadCacheConfiguration
Créer le cache comme étant de type RefreshAheadCache en lui donnant en parametre un cache basique que vous aurez crée et l'instance de  RefreshAheadCacheConfiguration.

Maintenant que votre cache connait sa configuration, il vous faut encore définir comment il va mettre ses données un jour.
Pour cela, il vous faudra utiliser un object de classe CacheLoader et notamment la méthode loadAll qui sera appelée par RefreshAhead.


##Exercice 6 : Bonus : ScheduleRefresh
ScheduleRefresh permet d'avoir un cache qui se rafraichit en arrière plan de manière automatique.
Créer un cache ScheduleRefresh qui se met à jour toutes les 3 secondes après le lancement du test.

Vous pourrez utiliser le meme cacheLoader que définit à l'exercice 5.
La configuration se fera via l'objet ScheduledRefreshConfiguration.
 Pour info, si vous souhaitez une mise à jour qui s'effectue 3 secondes après le lancement du test l'expression CRON
 est la suivante : second + "/1 * * * * ?" avec  int second = (new GregorianCalendar().get(Calendar.SECOND) + 3) % 60;
  Pour attacher la configuration au cache, vous passerez via l'objet  ScheduledRefreshCacheExtension.

  Comme pour l'exercice précedent, il vous faudra alors attacher votre cacheLoader à votre cache. Vous pouvez réutilisez le meme que précedemment.



#Partie 2

##Exercice 7 : Search

##Exercice 8 - Bonus : Cache ou datastore ? - FRS
Plus aucun appel à la base de données.
Toutes les données seront placées dans le cache et celui-ci, meme en cas de crash, gardera en mémoire les données.

Indice : Pour stocker les données, il faut les placer sur le disque. Pour ca, il vous faudra activer l'option Fast Restart
et définir lors de la configuration du cachemanager le path où seront stockés les données

Aller ensuite sur la page exercise8.html insere une nouvelle valeur dans le cache par seconde. Elle affiche le nombre d'enregistrements.
Tuer brutalement jetty (killall java par exemple).
Relancer l'application et vérifier que le nombre d'élements est resté stable.


##Exercice 9 - Bonus : ARC

#Partie 3

##Exercice 10 : BigMemory

##Exercice 11 : Clustering - sur la meme machine
             Télécharger le dernier kit sur le site de terracotta.
             Lancer un serveur terracotta.
             Modifier la classe WriteToCache pour y ajouter la configuration qui va se connecter au L2 (1er Client). Exécuter ensuite la class, elle écrira 10 nouveaux enregistrements dans le cache.
             Modifier la classe ReadFromCache pour y ajouter la configuration qui va se connecter au L2 (1er Client). Exécuter ensuite la class, elle lira l'ensemble des enregistrements dans le cache.

             Lancer la classe CacheReader qui va se connecter au L2 (2eme Client) et y lire les données.

##Exercice 12 : Clustering classique
              Trouver un autre binome pour faire l'exercice.
              1 binome lance un serveur.
              Les 2 binomes s'y connectent, l'un pour écrire des données, l'autre pour les lire.

##Exercice 13 : Un cache hautement disponible - réplication
              Trouver un autre binome pour faire l'exercice.
              1 binome lance un serveur actif
              1 binome lance un serveur passif
              Les 2 binomes se connectent sur l'actif.
              Ecrivez des données.
              Arreter (brutalement ou non) le serveur actif.
              Vérifiez qu'automatiquement, les clients utilisent désormais le passif et que les données sont toujours bien
               présentes.
##Exercice 14 : Un cache partionné
             Trouver un autre binome pour faire l'exercice.
             1 binome lance un serveur actif
             1 binome lance un autre serveur actif
             Ecrivez des données.
             Regarder via la TMC ce qu'il se passe.


#Partie 4

##Exercice 15  : WAN http://ehcache.org/documentation/wan-replication

