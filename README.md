# FlightLive - Applicazione Web di Prenotazione Voli

**FlightLive** è un'applicazione web full-stack sviluppata con **Java Spring Boot** che simula una piattaforma completa per la prenotazione di biglietti aerei. Il sistema offre un'interfaccia dedicata per gli utenti che desiderano cercare e prenotare voli, e un pannello di amministrazione per la gestione completa dell'operativo aereo.

## ✨ Funzionalità Principali

L'applicazione è divisa in due aree principali con funzionalità distinte basate sui ruoli utente.

### 👤 Area Utente (`ROLE_USER`)
- **Ricerca Voli Dinamica**: Visualizza una lista di voli futuri disponibili, con la possibilità di filtrare in tempo reale per aeroporto di destinazione.
- **Autenticazione Sicura**: Processo di registrazione e login con password crittografate tramite BCrypt.
- **Flusso di Prenotazione Avanzato**: Seleziona un volo, scegli il numero di posti desiderati da un form dedicato e conferma la prenotazione.
- **Area Personale "Le mie Prenotazioni"**:
    - Visualizza una cronologia completa di tutte le prenotazioni effettuate.
    - **Cancella** una prenotazione esistente (i posti vengono automaticamente restituiti e resi disponibili).
    - **Scarica Biglietto in PDF**: Genera e scarica un riepilogo della prenotazione in formato PDF grazie all'integrazione con la libreria iText.
- **Interfaccia Utente Moderna**: Un layout pulito e responsivo con un logo personalizzato e una navigazione intuitiva.

### 🛠️ Area Amministratore (`ROLE_ADMIN`)
- **Pannello di Controllo Protetto**: Accesso a una dashboard riservata solo agli amministratori.
- **Gestione Completa dei Voli (CRUD)**:
    - **Crea**: Aggiungi nuovi voli al sistema tramite un form dedicato.
    - **Leggi**: Visualizza la lista completa di tutti i voli (passati e futuri).
    - **Modifica**: Aggiorna i dettagli di un volo esistente (orari, posti, prezzi).
    - **Elimina**: Rimuovi un volo dal sistema.
- **Reporting Settimanale**: Genera un report dei voli più prenotati, ordinati per numero di posti venduti nell'ultima settimana, per analisi di business.


## 📸 Anteprima dell'Applicazione

Ecco alcuni screenshot che mostrano le interfacce principali dell'applicazione.

![loginPerPrenotare](https://github.com/user-attachments/assets/65f5082f-6f9c-4921-8729-14200a1a1a80)



![le miei prenotazioni](https://github.com/user-attachments/assets/187a04e5-e274-4f91-b672-b45fde1661c3)



## 🚀 Tecnologie Utilizzate

- **Backend**:
  - Java 17
  - Spring Boot 3.3.1
  - Spring MVC (per la gestione delle richieste web)
  - Spring Data JPA (con Hibernate per l'Object-Relational Mapping)
  - Spring Security (per l'autenticazione e l'autorizzazione basata sui ruoli)
- **Frontend**:
  - HTML5 e CSS3
  - Thymeleaf (come template engine per il rendering dinamico delle pagine)
  - Thymeleaf Layout Dialect (per la creazione di layout riutilizzabili)
- **Database**:
  - MySQL
- **Librerie Chiave**:
  - **iText 7**: Per la generazione dinamica dei biglietti in formato PDF.
  - **Maven**: Come build tool e per la gestione delle dipendenze.
- **Sviluppo e Deploy**:
  - **Git & GitHub**: Per il version control e la condivisione del codice.
  - **Server Web Integrato**: Apache Tomcat (fornito da Spring Boot).

## ⚙️ Istruzioni per l'Avvio Locale

Per eseguire il progetto sul tuo computer, segui questi passaggi:

1.  **Prerequisiti**:
    - JDK 17 o superiore installato.
    - Maven 3.6+ configurato nel tuo PATH.
    - Un'istanza di MySQL (versione 8.0+) in esecuzione.

2.  **Clonare il Repository**:
    ```bash
    git clone https://github.com/TUO_USERNAME/flightlive-app.git
    cd flightlive-app
    ```

3.  **Configurare il Database**:
    - Crea uno schema vuoto nel tuo MySQL chiamato `flightlive_db`:
      ```sql
      CREATE DATABASE IF NOT EXISTS flightlive_db;
      ```
    - Apri il file `src/main/resources/application.properties`.
    - Modifica le seguenti proprietà con le tue credenziali MySQL:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/flightlive_db
      spring.datasource.username=TUO_UTENTE_DB
      spring.datasource.password=TUA_PASSWORD_DB
      ```
    - Le tabelle verranno create automaticamente al primo avvio grazie alla configurazione di Hibernate (`ddl-auto: update`).

4.  **Eseguire l'Applicazione**:
    - Da riga di comando, usa il wrapper di Maven per avviare il progetto:
      ```bash
      ./mvnw spring-boot:run
      ```
      (Su Windows, usa `mvnw.cmd spring-boot:run`)
    - In alternativa, importa il progetto come "Existing Maven Project" nel tuo IDE (Eclipse, IntelliJ) e avvia la classe `FlightLiveApplication.java`.

5.  **Accesso e Dati di Test**:
    - L'applicazione sarà disponibile all'indirizzo [http://localhost:8080](http://localhost:8080).
    - Al primo avvio, la classe `DataLoader` popolerà il database con una ricca lista di voli di esempio e due utenti predefiniti.
    - **Credenziali Amministratore**:
      - **Email:** `admin@example.com`
      - **Password:** `password`
    - **Credenziali Utente Standard**:
      - **Email:** `user@example.com`
      - **Password:** `password`

## 📄 Schema del Database (`dump.sql`)

Lo schema delle tabelle viene creato automaticamente da Hibernate. Questo script è fornito come riferimento.

```sql
CREATE DATABASE IF NOT EXISTS `flightlive_db`;
USE `flightlive_db`;

-- Tabella per gli utenti e i loro ruoli
CREATE TABLE `utenti` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ruolo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_email` (`email`)
);

-- Tabella per i voli
CREATE TABLE `voli` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `aeroporto_arrivo` varchar(255) DEFAULT NULL,
  `aeroporto_partenza` varchar(255) DEFAULT NULL,
  `data_ora` datetime(6) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `posti_disponibili` int NOT NULL,
  `prezzo` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- Tabella di join per le prenotazioni
CREATE TABLE `prenotazioni` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_prenotazione` date DEFAULT NULL,
  `n_posti` int NOT NULL,
  `id_utente` bigint NOT NULL,
  `id_volo` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_prenotazioni_utenti` FOREIGN KEY (`id_utente`) REFERENCES `utenti` (`id`),
  CONSTRAINT `FK_prenotazioni_voli` FOREIGN KEY (`id_volo`) REFERENCES `voli` (`id`)
);
