# Duel sur la Toile  
## IUT'O Projet 1A 2017 - 2018  

---

### À propos  
Le projet *Duel sur la Toile* est un travail de groupe correspondant au projet obligatoire pour le deuxième semestre dans l'I.U.T. informatique d'Orléans.  

Ce dépôt GitLab constitue le rendu du projet pour le groupe 1A31-B (2017 - 2018).

---

### Utilisation du dépôt  

Les lignes indiquées sur fond noir doivent être tapées dans un terminal Linux sur lequel Git est installé ou dans un terminal Git sous Microsoft Windows.  

**1- Récupération du dépôt**  

```
  git clone https://gitlab.com/IUT45-2018/groupe31B.git
  git checkout votre_prenom_en_minuscule
```

**2- Sélection de branche**

```
  git checkout votre_prenom_en_minuscule
```

**3- Fusionner les modifications d'une autre branche sur la vôtre**

```
  git fetch --all
  git merge origin/prenom_de_la_personne_en_minuscule
```

Remarque : Pour fusionner votre branche avec la branche principale, spécifiez la branche *origin/master*.

**4- Ajouter un ou plusieurs fichiers dans le dépôt**
```
  git add chemin/vers/le/nouveau/fichier
```

Remarque : Vous pouvez spécifier un répertoire pour l'ajouter lui et son contenu.  
Évitez d'utiliser la commande *git add .* car celle-ci ajoute également certains fichiers non souhaités tels que les fichiers cachés des outils de développement tels qu'IDEA IntelliJ ou Eclipse.  

**5- Soumettre vos modifications**  
```
  git commit -am "Message explicatif des modifications apportées."  
```
Remarque : Assurez-vous d'être sur votre branche personnelle avant de réaliser un *commit*.  

**6- Envoyer les données sur le dépôt en ligne.**  
```
  git push
```
Remarque : Assurez-vous d'être sur votre branche personnelle avant de téléverser vos *commits*.  

---

### Aide supplémentaire  

**Afficher l'état de votre branche**  
Il peut être utile de vouloir voir quels fichiers ont été ajoutés, modifiés et supprimés dans votre répertoire de travail depuis votre dernier *commit*.
Pour consulter l'état de votre branche, vous pouvez faire usage de la commande :  
```
  git status
```

**Abandonner les modifications depuis le dernier commit**  
Il est impossible de fusionner une autre branche sur la vôtre tant que vous avez des modifications non enregistrées (c'est-à-dire sans qu'elles ne soient sauvegardées dans un *commit*).
Vous pourriez également vouloir abandonner vos dernières modifications. Pour ce faire, utilisez la commande :
```
  git stash
```
Remarque : Cette commande est **irréversible**. Faites-en usage avec précaution.  

**Afficher l'historique de vos modifications**  
Vous pouvez vouloir voir l'historique de vos modifications, en particulier si vous souhaitez revenir en arrière dans celles-ci.
Voici une commande pour visualiser l'historique des modifications sur votre branche :  
```
  git log --pretty=format:"%h - %an, %ar : %s"
```

**Consulter un état antérieur de la branche**  
Dans certains cas, vous pouvez vouloir revenir en arrière pour consulter votre ancien code.  
```
  git checkout hash_du_commit
```
Le hash d'un *commit* peut être affiché en utilisant la commande de l'historique ; il s'agit de la suite de lettres et de chiffres en début de chaque ligne de *commit*.  

**Écraser les modifications et revenir à un état antérieur**  
Si vous avez apporté une ou plusieurs modifications sur votre branche et que vous souhaitez les effacer, vous pouvez employer la commande :  
```
  git reset --hard hash_du_commit_auquel_vous_souhaitez_revenir
```
Le hash d'un *commit* peut être obtenu en utilisant la commande d'historique ; il se compose des suites de chiffres et de lettres en début de ligne.  
**Remarque :** L'utilisation de cette commande est **irréversible**. Elle doit seulement être utilisée sur votre branche personnelle. Soyez très vigilants.
---
© 2018 Midona  
Groupe 1A31B - Année universitaire 2017 - 2018. Tous droits réservés.