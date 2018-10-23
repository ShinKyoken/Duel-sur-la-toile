#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------

drop table ENVOYER;
drop table ETRE_AMI;
drop table APPARTENIR;
drop table CORRESPONDRE;
drop table INVITATION;
drop table RAPPORT;
drop table STATISTIQUE_UTILISATEUR;
drop table CONSULTER;
drop table MESSAGE;
drop table CONVERSATION;
drop table PARTIE;
drop table UTILISATEUR;
drop table JEU;
drop table STATISTIQUE_PLATEFORME;

#------------------------------------------------------------
# Table: UTILISATEUR
#------------------------------------------------------------

CREATE TABLE UTILISATEUR(
        pseudoUtilisateur Varchar (50) NOT NULL ,
        mailUtilisateur   Varchar (50) NOT NULL ,
        passwdUtilisateur Varchar (50) NOT NULL ,
        roleUtilisateur   Varchar (50) DEFAULT 'Utilisateur',
        activeUtilisateur Bool DEFAULT 1,
        PRIMARY KEY (pseudoUtilisateur)
);


#------------------------------------------------------------
# Table: JEU
#------------------------------------------------------------

CREATE TABLE JEU(
        idJeu     int Auto_increment NOT NULL ,
        nomJeu    Varchar (50) NOT NULL ,
        descJeu   Varchar (150) NOT NULL ,
        sourceJeu Blob NOT NULL ,
        typeJeu   Varchar (50) NOT NULL ,
        dispoJeu  Bool NOT NULL ,
        PRIMARY KEY (idJeu)
);

#------------------------------------------------------------
# Table: CORRESPONDRE
#------------------------------------------------------------

CREATE TABLE CORRESPONDRE(
        pseudoUtilisateur     Varchar (50) NOT NULL,
        idPartie              int NOT NULL ,
        score                 int NOT NULL,
        PRIMARY KEY (idPartie, pseudoUtilisateur)
);

#------------------------------------------------------------
# Table: INVITATION
#------------------------------------------------------------

CREATE TABLE INVITATION(
        idInvite     int Auto_increment  NOT NULL ,
        dateInvite   Date NOT NULL ,
        typeInvite   Varchar (25) NOT NULL ,
        idPartie     int NOT NULL ,
        PRIMARY KEY (idInvite)
);


#------------------------------------------------------------
# Table: PARTIE
#------------------------------------------------------------

CREATE TABLE PARTIE(
        idPartie          int Auto_increment  NOT NULL ,
        datePartie        Date NOT NULL ,
        numEtapePartie    int NOT NULL ,
        infoJSONPartie    Varchar (50) NOT NULL ,
        idJeu             int NOT NULL ,
        PRIMARY KEY (idPartie )
);


#------------------------------------------------------------
# Table: RAPPORT
#------------------------------------------------------------

CREATE TABLE RAPPORT(
        idRapport         int Auto_increment  NOT NULL ,
        champTexteRapport Varchar (125) NOT NULL ,
        pseudoUtilisateur Varchar (50) NOT NULL ,
        PRIMARY KEY (idRapport )
);


#------------------------------------------------------------
# Table: STATISTIQUE_UTILISATEUR
#------------------------------------------------------------

CREATE TABLE STATISTIQUE_UTILISATEUR(
        idStat            int Auto_increment  NOT NULL ,
        avgPtsStat        Float NOT NULL DEFAULT 0.0,
        frecJeuStat       Int NOT NULL DEFAULT 0,
        pseudoUtilisateur Varchar (50) NOT NULL ,
        PRIMARY KEY (idStat )
);

#------------------------------------------------------------
# Table: STATISTIQUE_PLATEFORME
#------------------------------------------------------------

CREATE TABLE STATISTIQUE_PLATEFORME(
        idStatPlateforme       int Auto_increment  NOT NULL ,
        nbUtilisateurConnecte  int NOT NULL ,
        PRIMARY KEY (idStatPlateforme)
);

#------------------------------------------------------------
# Table: CONSULTER
#------------------------------------------------------------

CREATE TABLE CONSULTER(
        idStatPlateforme       int NOT NULL ,
        pseudoUtilisateur      Varchar(50) NOT NULL ,
        PRIMARY KEY (idStatPlateforme, pseudoUtilisateur)
);


#------------------------------------------------------------
# Table: MESSAGE
#------------------------------------------------------------

CREATE TABLE MESSAGE(
        idMessage       int Auto_increment  NOT NULL ,
        destMessage     Varchar (125) NOT NULL ,
        corpMessage     Varchar (50) NOT NULL ,
        estLuMessage    Bool NOT NULL DEFAULT 0,
        nbCaractMessage Int NOT NULL DEFAULT 125,
        idConv    int NOT NULL ,
        PRIMARY KEY (idMessage )
);


#------------------------------------------------------------
# Table: CONVERSATION
#------------------------------------------------------------

CREATE TABLE CONVERSATION(
        idConv    int Auto_increment NOT NULL ,
        PRIMARY KEY (idConv)
);


#------------------------------------------------------------
# Table: ENVOYER
#------------------------------------------------------------

CREATE TABLE ENVOYER(
        pseudoUtilisateur Varchar (50) NOT NULL ,
        idInvite          Int NOT NULL ,
        destinataire      Varchar (50),
        PRIMARY KEY (pseudoUtilisateur ,idInvite )
);


#------------------------------------------------------------
# Table: Être_ami
#------------------------------------------------------------

CREATE TABLE ETRE_AMI(
        pseudoUtilisateur   Varchar (50) NOT NULL ,
        pseudoUtilisateur_1 Varchar (50) NOT NULL ,
        PRIMARY KEY (pseudoUtilisateur ,pseudoUtilisateur_1 )
);


#------------------------------------------------------------
# Table: APPARTENIR
#------------------------------------------------------------

CREATE TABLE APPARTENIR(
        idConv            Int NOT NULL ,
        pseudoUtilisateur Varchar (50) NOT NULL ,
        PRIMARY KEY (idConv ,pseudoUtilisateur )
);

ALTER TABLE INVITATION ADD CONSTRAINT FK_INVITATION_idPartie FOREIGN KEY (idPartie) REFERENCES PARTIE(idPartie);
ALTER TABLE PARTIE ADD CONSTRAINT FK_PARTIE_idJeu FOREIGN KEY (idJeu) REFERENCES JEU(idJeu);
ALTER TABLE RAPPORT ADD CONSTRAINT FK_RAPPORT_pseudoUtilisateur FOREIGN KEY (pseudoUtilisateur) REFERENCES UTILISATEUR(pseudoUtilisateur);
ALTER TABLE STATISTIQUE_UTILISATEUR ADD CONSTRAINT FK_STATISTIQUE_UTILISATEUR_pseudoUtilisateur FOREIGN KEY (pseudoUtilisateur) REFERENCES UTILISATEUR(pseudoUtilisateur);
ALTER TABLE CONSULTER ADD CONSTRAINT FK_CONSULTER_pseudoUtilisateur FOREIGN KEY (pseudoUtilisateur) REFERENCES UTILISATEUR(pseudoUtilisateur);
ALTER TABLE CONSULTER ADD CONSTRAINT FK_CONSULTER_idStatPlateforme FOREIGN KEY (idStatPlateforme) REFERENCES STATISTIQUE_PLATEFORME(idStatPlateforme);
ALTER TABLE MESSAGE ADD CONSTRAINT FK_MESSAGE_idConv FOREIGN KEY (idConv) REFERENCES CONVERSATION(idConv);
ALTER TABLE ENVOYER ADD CONSTRAINT FK_ENVOYER_pseudoUtilisateur FOREIGN KEY (pseudoUtilisateur) REFERENCES UTILISATEUR(pseudoUtilisateur);
ALTER TABLE ENVOYER ADD CONSTRAINT FK_ENVOYER_idInvite FOREIGN KEY (idInvite) REFERENCES INVITATION(idInvite);
ALTER TABLE ENVOYER ADD CONSTRAINT FK_ENVOYER_destinataire FOREIGN KEY (destinataire) REFERENCES UTILISATEUR(pseudoUtilisateur);
ALTER TABLE ETRE_AMI ADD CONSTRAINT FK_ETRE_AMI_pseudoUtilisateur FOREIGN KEY (pseudoUtilisateur) REFERENCES UTILISATEUR(pseudoUtilisateur);
ALTER TABLE ETRE_AMI ADD CONSTRAINT FK_ETRE_AMI_pseudoUtilisateur_1 FOREIGN KEY (pseudoUtilisateur_1) REFERENCES UTILISATEUR(pseudoUtilisateur);
ALTER TABLE APPARTENIR ADD CONSTRAINT FK_APPARTENIR_idConv FOREIGN KEY (idConv) REFERENCES CONVERSATION(idConv);
ALTER TABLE APPARTENIR ADD CONSTRAINT FK_APPARTENIR_pseudoUtilisateur FOREIGN KEY (pseudoUtilisateur) REFERENCES UTILISATEUR(pseudoUtilisateur);
ALTER TABLE CORRESPONDRE ADD CONSTRAINT FK_CORRESPONDRE_pseudoUtilisateur FOREIGN KEY (pseudoUtilisateur) REFERENCES UTILISATEUR(pseudoUtilisateur);
ALTER TABLE CORRESPONDRE ADD CONSTRAINT FK_CORRESPONDRE_idPartie FOREIGN KEY (idPartie) REFERENCES PARTIE(idPartie);
