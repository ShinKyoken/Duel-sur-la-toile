#------------------------------------------------------------
# Test
#------------------------------------------------------------

insert into UTILISATEUR (pseudoUtilisateur, mailUtilisateur, passwdUtilisateur, roleUtilisateur, activeUtilisateur) values
  ('Telikath', 'Telikathpro@gmail.com', 'mesCouillesenski','Administrateur',1),
  ('iutc', 'iutcDeru@gmail.com', 'tescouillesenski','Utilisateur',0),
  ('iuto', 'iuto@gmail.com', 'iuto','Utilisateur',1);

insert into JEU (idJeu,nomJeu,descJeu,sourceJeu,typeJeu,dispoJeu) values
  (1,'Puissance 4','kfghreqghuhieghieh','michel.png','FPS',1);

insert into STATISTIQUE_UTILISATEUR(avgPtsStat, frecJeuStat, pseudoUtilisateur) values
  (1.1,1,'Telikath');

insert into STATISTIQUE_PLATEFORME(nbUtilisateurConnecte) values
  (50),
  (25);

insert into CONSULTER(pseudoUtilisateur, idStatPlateforme) values
  ('Telikath',1),
  ('Telikath',2);

insert into RAPPORT (champTexteRapport, pseudoUtilisateur) values
  ('ihihi','iutc');

insert into CONVERSATION(idConv) values
  (1);

insert into CONVERSATION(idConv) values
  (2);

insert into MESSAGE(destMessage, corpMessage, estLuMessage, idConv) values
  ('burburgbu','oohoirhtih',true,1);
insert into MESSAGE(destMessage, corpMessage, idConv) values
  ('burburgbu','oohoirhtih',2),
  ('aeeeeeeeeeee','osfsfsfsfsfs',2),
  ('ggggggggggggggggggggggggggg','dgdgnjdnjd',2),
  ('bbbbbbbbbbbb','gggggggggggggggg',2);

insert into APPARTENIR(idConv, pseudoUtilisateur) values
  (1,'Telikath'),
  (1,'iutc');
insert into APPARTENIR(idConv, pseudoUtilisateur) values
  (2,'iuto');


insert into PARTIE(idPartie, datePartie, numEtapePartie, infoJSONPartie, idJeu) values
  (1,STR_TO_DATE("23/1/1980","%d/%m/%Y"),1,'fichier JSON',1),
  (2,STR_TO_DATE("23/1/1980","%d/%m/%Y"),-1,'fichier JSON',1),
  (3,STR_TO_DATE("23/1/1980","%d/%m/%Y"),1,'fichier JSON',1),
  (4,STR_TO_DATE("23/1/1980","%d/%m/%Y"),1,'fichier JSON',1);

insert into INVITATION(dateInvite,typeInvite,idPartie) values
  (STR_TO_DATE('23/1/1980','%d/%m/%Y'),'michel',1);

insert into ENVOYER(pseudoUtilisateur, idInvite, destinataire) values
  ('Telikath',1,'iutc');

insert into ETRE_AMI(pseudoUtilisateur, pseudoUtilisateur_1) values
  ('Telikath','iutc');

insert into CORRESPONDRE(pseudoUtilisateur, idPartie, score) values
  ('Telikath',1,20),
  ('iuto',2,10),
  ('iutc',2,5),
  ('Telikath',4,5),
  ('iuto',4,10),
  ('iutc',1,20);
