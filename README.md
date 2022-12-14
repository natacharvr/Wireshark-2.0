# Wireshark-2.0
projet LU3IN033 : groupe 1, Natacha Rivière, Mathis Romero

## Description :
Programmation d'un visualisateur de flux de trafic réseau. Notre analyseur prend en charge des trames échangées entre deux machines.
Notre analyseur de réseau prend en entrée une fichier texte dans lequel sont enregistrées des trames auparavant enregistrées sur un réseau Ethernet.
Les protocoles suivants sont reconnus :

- Couche 2 : Ethernet
- Couche 3 : IPv4
- Couche 4 : TCP
- Couche 7 : HTTP

### Fonctionnement
Le programme demande la sélection d'un fichier texte. Ce fichier doit être composé de trames de la forme :
XXXX  YY YY YY YY YY ... YY YY YY YY YY YY YY zzzzzzzzzzzzzzzzzzzzzzzzzzz
avec :

- XXXX l'indice du premier octet de la ligne, suivi de 1 espace ou plus;
- YY YY les octets de la ligne, entre 1 et n, séparés par des espaces;
- zzzz des caractères facultatifs qui correspondent à la transcription en ascii dans Wireshark, qui sont éliminés.

Le programme coupe le texte en plusieurs sous string qui sont données à des objet Trame qui s'occupent du découpage interne des informations des trames.
Ces trames sont découpées en utilisant l'indice 0000 de la première ligne de la trame suivante. 
Les découpages internes s'opèrent en sélectionnant les bons indices dans la string.

### Résultat
Le programme ouvre une fenêtre graphique qui représente avec des flèches les échanges de trames entre différentes machines. Les informations utiles des trames sont données sur la droite du graphique, alignées avec la flèche correspondante.
Pendant que la fenêtre est ouverte, le programme exporte le résultat graphique sous forme d'image *jpg* dans le dossier courant. Le fichier porte le même nom que le fichier fourni. Si vous donnez ``test.txt`` le fichier exporté sera nommé ``test.jpg``.