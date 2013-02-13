=====================================================
Hands on Duchess fr - Cache et BigData

Code from the backbone cellar application developped by Christone Coenraets - https://github.com/ccoenraets/backbone-jax-cellar

====================================================

#Prérequis :

    Maven
    Git
    Bigmemory go + license http://www.terracotta.org/downloads/bigmemorygo?set=1


export MAVEN_OPTS="-XX:MaxDirectMemorySize=10G -Xmx2G -Xms300m"
mvn jetty:run -Dmaven.test.skip=true


#Exercice 1 :  Cache Aside
Implémenter un cache aside dans le service Exercise1 pour la méthode findBy.
Un cache aside a l'algo suivant :
Si j'ai la valeur dans mon cache alors je la retourne
Sinon je vais la chercher en base de données et je la place dans mon cache

Une fois que vous etes confiant, rendez-vous sur la page exercise1.html  . Vous y verrez 2 lignes pour comparer la latence entre un appel à la base de données et l'équivalent en cache.


#Exercice 2 :  Read-Trough
Cache sytem-of-record : l'application ne voit plus que le cache, c'est le garant des données.
Implémenter la lecture via la méthode findBy de Exercise2. Meme lorsque la donnée ne sera pas déja présente dans le cache,
c'est le cache qui saura comment aller la chercher.
Indice : SelfPopulatingCache pourra surement vous aider :)


Rendez vous sur la page exercise2.html pour voir le résultat

#Exercice 3 : Write-Through
Cache as a sytem-of-record : l'application ne voit plus que le cache, c'est le garant des données.
Implémenter l'écriture via la méthode create de Exercise3. La donnée sera écrite dans le cache et c'est le cache qui saura reporter la donnée dans la base de données secondaires, ici mysql
L'écriture devra etre synchronisée.
 
Indice : Le cacheWriter pourra surement vous aider :)
 
Rendez vous sur la page exercise3.html pour voir le résultat

#Exercice 4 : Write-Behind
Cache as a sytem-of-record : l'application ne voit plus que le cache, c'est le garant des données.
Implémenter l'écriture via la méthode create  de Exercise3. La donnée sera écrite dans le cache et c'est le cache qui saura reporter la donnée dans la base de données secondaires, ici mysql
L'écriture devra etre asynchone.
Rendez vous sur la page exercise4.html pour voir le résultat

#Exercice 5 - Bonus : Cache ou datastore ?
Plus aucun appel à la base de données.
Toutes les données seront placées dans le cache et celui-ci, meme en cas de crash, gardera en mémoire les données.

Indice : Pour stocker les données, il faut les placer sur le disque. Pour ca, il vous faudra activer l'option Fast Restart
et définir lors de la configuration du cachemanager le path où seront stockés les données

Aller ensuite sur la page exercise5.html : celle-ci insère une nouvelle valeur dans le cache par seconde. Elle affiche le nombre d'enregistrements.
Tuer brutalement jetty (killall java par exemple).
Relancer l'application et vérifier que le nombre d'élements est resté stable.


#Exercice 6 - Bonus : Search

#Exercice 7 - Bonus : ARC

#Exercice 8 : BigMemory

#Exercice 9 : Clustering - sur la meme machine
Télécharger le dernier kit sur le site de terracotta.
Lancer un serveur terracotta
Lancer la classe CacheWriter qui va se connecter au L2 (1er Client) et y charger des données
Lancer la classe CacheReader qui va se connecter au L2 (2eme Client) et y lire les données.

#Exercice 10 : Clustering classique
 Trouver un autre binome pour faire l'exercice.
 1 binome lance un serveur.
 Les 2 binomes s'y connectent, l'un pour écrire des données, l'autre pour les lire.

#Exercice 11 : Un cache hautement disponible - réplication
 Trouver un autre binome pour faire l'exercice.
 1 binome lance un serveur actif
 1 binome lance un serveur passif
 Les 2 binomes se connectent sur l'actif.
 Ecrivez des données.
 Arreter (brutalement ou non) le serveur actif.
 Vérifiez qu'automatiquement, les clients utilisent désormais le passif et que les données sont toujours bien
  présentes.
#Exercice 12  :
Télecharger ActiveMQ
Lancer le
Essayer de faire fonctionner un cache pour qu il se connecte automatiquement au bridge WAN.
WAN http://ehcache.org/documentation/wan-replication



