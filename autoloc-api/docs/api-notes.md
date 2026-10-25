# API Notes - Atelier 5

## A. Endpoints

| Controleur | Verbe + route | Service appele | Succes | Erreurs |
| --- | --- | --- | --- | --- |
| `AgenceController` | `GET /api/agences` | `IAgenceService.findAll()` | `200` | - |
| `AgenceController` | `GET /api/agences/{id}` | `IAgenceService.findById(id)` | `200` | `404` |
| `AgenceController` | `POST /api/agences` | `IAgenceService.create(agence)` | `201` | `400` |
| `AgenceController` | `PUT /api/agences/{id}` | `IAgenceService.update(id, agence)` | `200` | `400`, `404` |
| `AgenceController` | `DELETE /api/agences/{id}` | `IAgenceService.deleteById(id)` | `204` | `404` |
| `EmployeController` | `GET /api/employes` | `IEmployeService.findAll()` | `200` | - |
| `EmployeController` | `GET /api/employes/{id}` | `IEmployeService.findById(id)` | `200` | `404` |
| `EmployeController` | `POST /api/employes` | `IEmployeService.create(employe)` | `201` | `400` |
| `EmployeController` | `PUT /api/employes/{id}` | `IEmployeService.update(id, employe)` | `200` | `400`, `404` |
| `EmployeController` | `DELETE /api/employes/{id}` | `IEmployeService.deleteById(id)` | `204` | `404` |
| `VehiculeController` | `GET /api/vehicules` | `IVehiculeService.findAll()` | `200` | - |
| `VehiculeController` | `GET /api/vehicules/{id}` | `IVehiculeService.findById(id)` | `200` | `404` |
| `VehiculeController` | `POST /api/vehicules` | `IVehiculeService.create(vehicule)` | `201` | `400` |
| `VehiculeController` | `PUT /api/vehicules/{id}` | `IVehiculeService.update(id, vehicule)` | `200` | `400`, `404` |
| `VehiculeController` | `DELETE /api/vehicules/{id}` | `IVehiculeService.deleteById(id)` | `204` | `404` |
| `EquipementController` | `GET /api/equipements` | `IEquipementService.findAll()` | `200` | - |
| `EquipementController` | `GET /api/equipements/{id}` | `IEquipementService.findById(id)` | `200` | `404` |
| `EquipementController` | `POST /api/equipements` | `IEquipementService.create(equipement)` | `201` | `400` |
| `EquipementController` | `PUT /api/equipements/{id}` | `IEquipementService.update(id, equipement)` | `200` | `400`, `404` |
| `EquipementController` | `DELETE /api/equipements/{id}` | `IEquipementService.deleteById(id)` | `204` | `404` |
| `ClientController` | `GET /api/clients` | `IClientService.findAll()` | `200` | - |
| `ClientController` | `GET /api/clients/{id}` | `IClientService.findById(id)` | `200` | `404` |
| `ClientController` | `POST /api/clients` | `IClientService.create(client)` | `201` | `400` |
| `ClientController` | `PUT /api/clients/{id}` | `IClientService.update(id, client)` | `200` | `400`, `404` |
| `ClientController` | `DELETE /api/clients/{id}` | `IClientService.deleteById(id)` | `204` | `404` |
| `ReservationController` | `GET /api/reservations` | `IReservationService.findAll()` | `200` | - |
| `ReservationController` | `GET /api/reservations/{id}` | `IReservationService.findById(id)` | `200` | `404` |
| `ReservationController` | `POST /api/reservations` | `IReservationService.create(reservation)` | `201` | `400` |
| `ReservationController` | `PUT /api/reservations/{id}` | `IReservationService.update(id, reservation)` | `200` | `400`, `404` |
| `ReservationController` | `DELETE /api/reservations/{id}` | `IReservationService.deleteById(id)` | `204` | `404` |
| `ContratController` | `GET /api/contrats` | `IContratService.findAll()` | `200` | - |
| `ContratController` | `GET /api/contrats/{id}` | `IContratService.findById(id)` | `200` | `404` |
| `ContratController` | `POST /api/contrats` | `IContratService.create(contrat)` | `201` | `400` |
| `ContratController` | `PUT /api/contrats/{id}` | `IContratService.update(id, contrat)` | `200` | `400`, `404` |
| `ContratController` | `DELETE /api/contrats/{id}` | `IContratService.deleteById(id)` | `204` | `404` |
| `PaiementController` | `GET /api/paiements` | `IPaiementService.findAll()` | `200` | - |
| `PaiementController` | `GET /api/paiements/{id}` | `IPaiementService.findById(id)` | `200` | `404` |
| `PaiementController` | `POST /api/paiements` | Non expose (lecture seule) | - | `405` |
| `MaintenanceController` | `GET /api/maintenances` | `IMaintenanceService.findAll()` | `200` | - |
| `MaintenanceController` | `GET /api/maintenances/{id}` | `IMaintenanceService.findById(id)` | `200` | `404` |
| `MaintenanceController` | `POST /api/maintenances` | `IMaintenanceService.create(maintenance)` | `201` | `400` |
| `MaintenanceController` | `PUT /api/maintenances/{id}` | `IMaintenanceService.update(id, maintenance)` | `200` | `400`, `404` |
| `MaintenanceController` | `DELETE /api/maintenances/{id}` | `IMaintenanceService.deleteById(id)` | `204` | `404` |

## B. Annotations Jackson

- `Contrat.paiements`: `@JsonManagedReference("contrat-paiements")`
- `Paiement.contrat`: `@JsonBackReference("contrat-paiements")`
- Justification: eviter la boucle infinie de serialisation sur la relation bidirectionnelle `Contrat <-> Paiement`.
- Difference:
  - `@JsonBackReference` exclut la reference inverse a la serialisation et fonctionne en paire avec `@JsonManagedReference`.
  - `@JsonIgnore` ignore simplement la propriete sans notion de paire parent/enfant.

## C. Postman et Swagger

- Postman sert a valider les cas nominaux et d'erreur (exemple: `GET /api/contrats/9999` retourne `404`).
- Swagger UI sert a explorer et documenter l'API generee automatiquement (tags, resumes, codes reponse).
- Les deux sont complementaires: Postman pour les scenarios de test repetables, Swagger pour le contrat API lisible et test rapide.

## D. Anomalies SonarQube

1. **Import duplique dans `ContratController`**
   - Regle: imports redondants/inutiles.
   - Correction: nettoyage des imports (suppression de doublons et wildcard).

2. **Type de `horodatage` incoherent dans `GlobalExceptionHandler`**
   - Regle: incoherence de structure de reponse API / maintainabilite.
   - Correction: conversion explicite en `String` UTC (`toString()`) pour `404` et `400`.

## E. Questions

1. **Quel composant recoit les requetes et mappe vers la methode du controleur ?**
   - `DispatcherServlet` recoit la requete puis s'appuie sur les mappings (`@RequestMapping`, `@GetMapping`, etc.) pour trouver la methode cible.

2. **Pourquoi le controleur depend de `I...Service` et pas de `Repository` ni de `...ServiceImpl` ?**
   - Pour respecter la separation des couches et inverser les dependances: le web depend d'une abstraction metier, pas d'une implementation ni de l'acces BD.

3. **Pourquoi `POST` = `201`, `DELETE` = `204`, absente = `404` et pas `500` ?**
   - `201` indique qu'une ressource est creee, `204` qu'elle est supprimee sans corps. `404` signale une ressource introuvable (erreur fonctionnelle attendue), alors que `500` est une erreur serveur inattendue.

4. **Pourquoi `PaiementController` expose seulement des `GET` ? Reponse d'un `POST /api/paiements` ?**
   - Les paiements sont geres via la cascade depuis `Contrat`. Aucun endpoint `POST` n'est defini sur `/paiements`, donc la reponse attendue est `405 Method Not Allowed`.

5. **Risques de l'exposition directe des entites JPA ? Pourquoi `@JsonManagedReference` sur `Contrat.paiements` et `@JsonIgnore` possible ailleurs ?**
   - Risques: couplage fort API/BD, exposition de champs internes, problemes LAZY/proxy et boucles JSON.
   - `@JsonManagedReference` est adapte a la composition parent-enfant (`Contrat` -> `Paiement`) avec reference retour.
   - `@JsonIgnore` est pratique pour masquer des liens inverses non utiles dans la reponse, sans logique parent/enfant.

