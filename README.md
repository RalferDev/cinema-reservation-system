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