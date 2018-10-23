#Les parties auquelle participe le joueur 'iuto'
select idPartie
from CORRESPONDRE
where pseudoUtilisateur='iuto';

#Les messages non lu du joueur 'iuto'
select corpMessage, DestMessage
from APPARTENIR natural join CONVERSATION natural join MESSAGE
where pseudoUtilisateur='iuto' and estLuMessage=false;

#Le nombre de parties gagnées par le joueur 'iuto' contre le joueur 'iutc' pour le jeu 'Puissance 4'
select count(idPartie) nbPartieGagnee
from PARTIE natural join JEU
where numEtapePartie=-1 and nomJeu='Puissance 4' and idPartie in (
  select j1.idPartie
  from CORRESPONDRE j1, CORRESPONDRE j2
  where j1.pseudoUtilisateur='iuto' and j2.pseudoUtilisateur='iutc' and j1.score > j2.score and j1.idPartie=j2.idPartie
);
