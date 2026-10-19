# Atelier 4 - Service notes

## A. Modes d'injection compares

| Critere | Injection par constructeur | Injection par attribut |
|---|---|---|
| Champ `final` possible | Oui | Non |
| Dependance visible dans la signature | Oui (constructeur) | Non |
| Utilisable avec `new` hors conteneur sans null | Oui, si dependance fournie | Non, risque de `null` |
| Signalement SonarQube for IDE | Pas d'alerte sur le mode | Regle S6813 (injection par attribut) |

## B. Services

| Service | Dependance injectee | Mode | Regle metier principale | Justification |
|---|---|---|---|---|
| `ContratServiceImpl` | `IContratRepository` | Constructeur (`final` + `@RequiredArgsConstructor`) | Refuser un id renseigne a la creation, un montant negatif, et conserver la collection `paiements` hors `update` | Service metier, dependance obligatoire, classe toujours valide |
| `AgenceServiceImpl` | `IAgenceRepository` | Constructeur | Refuser une agence sans nom, ville, adresse ou telephone | Meme convention, service sans etat |
| `ClientServiceImpl` | `IClientRepository` | Constructeur | Refuser un client sans nom, prenom, email, telephone, numero de permis ou date d'inscription | Meme convention, dependance obligatoire |
| `EmployeServiceImpl` | `IEmployeRepository` | Constructeur | Refuser un employe sans nom, prenom ou role | Meme convention, dependance obligatoire |
| `VehiculeServiceImpl` | `IVehiculeRepository` | Constructeur | Refuser un vehicule sans immatriculation, marque, modele, categorie, statut ou avec tarif negatif | Meme convention, dependance obligatoire |
| `EquipementServiceImpl` | `IEquipementRepository` | Constructeur | Refuser un equipement sans libelle | Meme convention, dependance obligatoire |
| `ReservationServiceImpl` | `IReservationRepository` | Constructeur | Refuser une reservation sans dates, sans statut, ou avec date de fin anterieure a la date de debut | Meme convention, dependance obligatoire |
| `MaintenanceServiceImpl` | `IMaintenanceRepository` | Constructeur | Refuser une maintenance sans dates, sans description, ou avec date de fin anterieure a la date de debut | Meme convention, dependance obligatoire |
| `PaiementServiceImpl` | `IPaiementRepository` | Constructeur | Lecture seule : seules `findById` et `findAll` sont exposees | Service lecture seule, dependance obligatoire |

Remarque : certaines regles du sujet ont ete adaptees aux attributs reellement presents dans le modele. Par exemple, `Maintenance` ne contient pas de champ `cout` et `Equipement` ne contient pas de champ `quantite`, donc les validations portent sur les champs effectivement disponibles.

## C. Messages d'erreur

1. **Message 1 (`IContratService` introuvable)**
   - Causes probables cote `ContratServiceImpl` :
     - annotation `@Service` absente ;
     - classe placee hors du package scanne (`tn.esprit.autoloc` et sous-packages).
   - Correction : ajouter `@Service` et verifier le package.

2. **Message 2 (2 beans trouves)**
   - Correction 1 : mettre `@Primary` sur une implementation (annotation sur la classe).
   - Correction 2 : utiliser `@Qualifier("nomBean")` au point d'injection (annotation sur le parametre du constructeur).

3. **Message 3 (cycle de dependances)**
   - Pourquoi constructeur : les dependances sont requises a l'instanciation, donc le cycle remonte immediatement et ne peut pas etre masque.
   - Piste de conception : extraire la responsabilite commune dans un troisieme service (ex. coordination) pour casser la reference mutuelle.

## D. Anomalies SonarQube for IDE

| Regle | Explication | Correction apportee |
|---|---|---|
| S6813 | Injection par attribut deconseillee | Injection par constructeur sur tous les services |
| S112 | Exception trop generique (`RuntimeException`) | Utilisation de `ResourceNotFoundException` pour les absences |
| Code dead/commentaire stale | Commentaire non actionnable / incoherent | Clarification et nettoyage des services pour garder un code lisible |

## E. Questions de comprehension

1. **Ou est le `new` de `ContratServiceImpl` et qui l'execute ?**
   - Le `new` est implicite : c'est le conteneur Spring IoC qui instancie le bean au demarrage.

2. **Pourquoi un controleur depend de `IContratService` et non `ContratServiceImpl` ?**
   - Pour dependre d'une abstraction, reduire le couplage et permettre de changer l'implementation sans modifier le controleur.

3. **Pourquoi un service singleton doit rester sans etat ? Exemple de bug.**
   - Le singleton est partage entre requetes/threads : un etat mutable peut fuiter entre utilisateurs.
   - Exemple : stocker un `clientCourant` dans un attribut de service provoque des melanges de donnees entre requetes concurrentes.

4. **Pourquoi `update` charge l'existant avant `save` ?**
   - Pour ne modifier que les champs autorises et eviter d'ecraser des colonnes/associations non envoyees par le client.

5. **Difference `@Component` vs `@Bean`, `@Primary` vs `@Qualifier`, et pourquoi `IPaiementService` est lecture seule ?**
   - `@Component` : bean detecte par scan sur la classe. `@Bean` : bean declare explicitement via methode de configuration.
   - `@Primary` : bean prefere par defaut en cas d'ambiguite de type. `@Qualifier` : choix explicite d'un bean precis.
   - `IPaiementService` est lecture seule pour imposer que la creation/modification/suppression de paiements passe par le cycle metier du contrat.

## F. Checklist de conformite Atelier 4

- `ResourceNotFoundException` presente dans `tn.esprit.autoloc.exception`
- 9 interfaces `I...Service` presentes dans `tn.esprit.autoloc.service`
- 9 classes `...ServiceImpl` presentes dans `tn.esprit.autoloc.service`
- Tous les services sont annotes `@Service`
- Toutes les dependances repository sont injectees par constructeur via champ `final` + `@RequiredArgsConstructor`
- Aucun `new` de repository dans la couche service
- Aucun `@Autowired` sur attribut dans la couche service
- `create` refuse un identifiant non null pour les services CRUD
- `findById` leve `ResourceNotFoundException` si la ressource est absente
- `update` charge l'existant avant `save`
- `deleteById` verifie l'existence avant suppression
- `IPaiementService` reste volontairement en lecture seule
- Aucun controleur ajoute dans ce livrable, conformement a la consigne actuelle

