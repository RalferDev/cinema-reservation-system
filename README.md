# Cinema Reservation System - Backend REST API

Sistema di gestione prenotazioni per un cinema, strutturato con un'architettura a microservizi (Spring Boot e PostgreSQL).

## Architettura
Il progetto è strutturato in un monorepo contenente due microservizi indipendenti:
- **Catalog Service (Porta 8081)**: Gestisce il catalogo di sale, posti, film e programmazione spettacoli.
- **Booking Service (Porta 8082)**: Gestisce il ciclo di vita delle prenotazioni e le logiche di concorrenza sui posti.

## Prerequisiti e Setup (Docker)
Per facilitare la valutazione e l'esecuzione locale, l'intero strato di persistenza è gestito tramite Docker Compose.

1. Assicurati di avere **Docker** e **Docker Compose** installati.
2. Clona la repository e apri il terminale nella cartella root del progetto.
3. Avvia i database PostgreSQL isolati per i due servizi:
   ```bash
   docker-compose up -d

## Prerequisiti e Setup Java
Per facilitare la valutazione consiglio di utilizzare IntellijIDEA come ide e avviare i due microservizi **catalog-service** e **booking-service** tramite un click sul play button presente sul lato sinistro della loro rispettiva classe principale (CatalogServiceApplication.java - BookingServiceApplication.java).

In alternativa bisognerà installare globalmente:

1. Java Development Kit (JDK): Versione 17 o superiore.
2. Apache Maven: Versione 3.8 o superiore.

Una volta effettuate le opportune installazioni aprire un terminale nelle cartelle principali dei due microservizi sopra citati e usare i comandi
```bash
mvn clean install
mvn spring-boot:run