-- Crea il database se non esiste già
CREATE DATABASE IF NOT EXISTS `flightlive_db` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Seleziona il database su cui lavorare
USE `flightlive_db`;

--
-- Struttura della tabella `utenti`
--
DROP TABLE IF EXISTS `utenti`;
CREATE TABLE `utenti` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ruolo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Struttura della tabella `voli`
--
DROP TABLE IF EXISTS `voli`;
CREATE TABLE `voli` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `aeroporto_arrivo` varchar(255) DEFAULT NULL,
  `aeroporto_partenza` varchar(255) DEFAULT NULL,
  `data_ora` datetime(6) DEFAULT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `posti_disponibili` int NOT NULL,
  `prezzo` decimal(10,2) DEFAULT NULL, -- Precisione 10, 2 decimali (es. 99999999.99)
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Struttura della tabella `prenotazioni`
--
DROP TABLE IF EXISTS `prenotazioni`;
CREATE TABLE `prenotazioni` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data_prenotazione` date DEFAULT NULL,
  `n_posti` int NOT NULL,
  `id_utente` bigint NOT NULL,
  `id_volo` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_utente` (`id_utente`),
  KEY `FK_volo` (`id_volo`),
  CONSTRAINT `FK_prenotazioni_utenti` FOREIGN KEY (`id_utente`) REFERENCES `utenti` (`id`),
  CONSTRAINT `FK_prenotazioni_voli` FOREIGN KEY (`id_volo`) REFERENCES `voli` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- NOTA SUI DATI:
-- I dati di esempio (utenti e voli) non sono inclusi in questo script.
-- Vengono generati e inseriti automaticamente all'avvio dell'applicazione
-- dalla classe DataLoader.java. Questo garantisce che:
-- 1. Le password degli utenti di default siano correttamente codificate con BCrypt.
-- 2. Le date dei voli di esempio siano sempre nel futuro rispetto alla data di avvio.