# Wireshark 2.0

Projet LU2IN033  : groupe 1, Natacha Rivière, Mathis Romero

### Comment utiliser le visualisateur de trames :

#### Configuration requise :

Pour lancer le programme, il est nécessaire d'avoir une version de java égale ou supérieure à _*Java 17*_. 

#### Procédure de lancement :

Le dossier fourni contient un fichier Wireshark-2.jar : dans le meilleur des cas, un simple double clic sur cette archive lance le programme. 

Si cette procédure ne fonctionne pas, plusieurs options s'offrent à vous : 

- Vérifiez votre version de Java, une version inférieure à _Java 17_ n'est pas compatible

- ouvrez un terminal dans le dossier contenant l'archive et utilisez la commande ``java -jar Wireshark2.jar``
- Si les options précédentes ne fonctionne pas non plus, un fichier Makefile vous est fourni. Utilisez la commande ```make main```qui créera un nouveau fichier .jar que vous pourrez lancer comme indiqué plus haut.

#### Utilisation du logiciel

Au lancement, il vous est demandé de fournir un fichier texte contenant la trace des trames que vous souhaitez analyser, vous pouvez utiliser le bouton `Ouvrir` pour accéder à l'explorateur de fichiers et parcourir votre arborescence pour sélectionner le fichier. Vous pouvez aussi fournir dans le champ texte au dessous le chemin absolu du fichier. Cliquez ensuite sur ``Valider``. 

Si vous fournissez un chemin de fichier inexistant, la procédure continue, vous n'obtiendrez aucune information intéressante, seulement un écran vide. 

Ensuite, une liste d'adresses Ip s'affiche, sélectionnez celles dont vous voulez voir les interactions et cliquez sur ``Valider``. 
