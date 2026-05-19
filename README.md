# Memory Trainer

A Spring Boot web application for training memory using flashcards.

## Prerequisites

- Java 17+
- Maven 3.9+

## Running

```bash
mvn spring-boot:run
```

The app starts at [http://localhost:8080](http://localhost:8080).

## Endpoints

| Path | Description |
|------|-------------|
| `/decks` | List all decks |
| `/decks/new` | Create a new deck |
| `/decks/{id}/edit` | Edit a deck |
| `/decks/{id}/cards` | List cards in a deck |
| `/decks/{id}/cards/new` | Add a card to a deck |
| `/decks/{id}/study` | Start a study session |
| `/h2-console` | H2 database console (JDBC URL: `jdbc:h2:file:./data/memorytrainer`) |

## Data Storage

Uses an embedded H2 database persisted to `./data/memorytrainer`. Data survives restarts.
